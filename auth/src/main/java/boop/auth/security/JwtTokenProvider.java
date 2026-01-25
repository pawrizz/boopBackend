package boop.auth.security;
import boop.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import boop.auth.api.dto.TokenResponse;
import boop.auth.token.RefreshTokenService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiry-ms}")
    private long expiryMs;
    @Autowired
    private RefreshTokenService refreshTokenService;
    



    public Claims parse(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Handle specifically if you want to trigger a refresh flow
            throw new RuntimeException(e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            // Handle tampered tokens, malformed strings, etc.
            throw new RuntimeException(e.getMessage());
        }
    }

    public TokenResponse generateAccessToken(User user) {

    return new TokenResponse(getAccessToken(user), null);
    }

    private String getAccessToken(User user) 
    {
        
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(user.getPublicId().toString())
                .claim("roles", user.getRoles())
                .addClaims(Map.of("type", "ACCESS"))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();

    }

    public TokenResponse generateRollingToken(User user) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        String accessToken = getAccessToken(user);
        String refreshToken = getRefreshToken(user,expiryMs);
        refreshTokenService.create(user.getId(),refreshToken);
        return new TokenResponse(accessToken,refreshToken);
    }

    private String getRefreshToken(User user,long expiryMs)
    {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(user.getPublicId().toString())
                .claim("type", "REFRESH")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
