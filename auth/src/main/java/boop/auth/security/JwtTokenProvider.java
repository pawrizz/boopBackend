package boop.auth.security;

import boop.common.security.Permission;
import boop.common.security.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiry-ms}")
    private long expiryMs;

    public String generate(
            Long userId,
            Set<Role> roles,
            Set<Permission> permissions) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().map(Enum::name).toList());
        claims.put("permissions", permissions.stream().map(Enum::name).toList());

        return Jwts.builder()
                .setSubject(userId.toString())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
