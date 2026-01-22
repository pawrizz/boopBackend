package boop.auth.token;

import boop.user.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final Duration WINDOW = Duration.ofDays(7);

    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    public RefreshToken create(User user) {

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setLastUsedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(WINDOW));

        return repo.save(token);
    }

    public RefreshToken verifyAndRotate(String tokenValue) {

        RefreshToken token = repo.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Session expired, re-login required");
        }

        // rolling window
        token.setLastUsedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(WINDOW));

        return repo.save(token);
    }

    public void revoke(String tokenValue) {
        repo.findByToken(tokenValue).ifPresent(repo::delete);
    }
}
