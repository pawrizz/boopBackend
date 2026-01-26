package boop.auth.domain.entity;

import boop.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 2048)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Instant lastUsedAt;

    @Column(nullable = true ,length = 512)
    private String deviceInfo;
    @Column(nullable = false)
    private Boolean revoked = false;


}
