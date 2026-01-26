package boop.auth.service;

import boop.auth.domain.entity.RefreshToken;
import boop.auth.domain.repo.RefreshTokenRepository;
import boop.auth.dto.TokenResponse;
import boop.auth.security.JwtTokenProvider;
import boop.user.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class RefreshTokenService {

    private static final Duration WINDOW = Duration.ofDays(7);

    private final RefreshTokenRepository repo;
    private final JwtTokenProvider jwt;

    public RefreshTokenService(RefreshTokenRepository repo, JwtTokenProvider jwt) {
        this.repo = repo;
        this.jwt = jwt;
    }

    public RefreshToken create(Long  userId,String refreshToken) {

        RefreshToken token = new RefreshToken();
        User user = new User();
        user.setId(userId);
        token.setUser(user);
        token.setToken(refreshToken);
        token.setLastUsedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(WINDOW));

        return repo.save(token);
    }



    public TokenResponse verifyAndRotate(String tokenValue) {

        TokenResponse response = null;

        try {
            Claims claims = jwt.parse(tokenValue);
            String publicId = claims.getSubject();
            RefreshToken token = repo.findByTokenAndUser_PublicId(tokenValue, java.util.UUID.fromString(publicId))
                    .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
            response = jwt.generateAccessToken(token.getUser());
        }catch (ExpiredJwtException e)
        {

        }

        return response;
    }

    public void revoke(String tokenValue) {
        repo.findByToken(tokenValue).ifPresent(repo::delete);
    }
}
