package boop.auth.domain.repo;

import boop.auth.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Ref;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByTokenAndUser_PublicId(String token, UUID publicId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE refresh_tokens rt SET rt.revoked = true WHERE u.publicId = :uuid")
    int updateRevokedStatus(UUID uuid, String phone);
    
}
