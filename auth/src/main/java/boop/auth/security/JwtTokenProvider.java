package boop.auth.security;

import boop.common.security.Permission;
import boop.common.security.Role;
import boop.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import boop.auth.api.dto.TokenResponse;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiry-ms}")
    private long expiryMs;

    public String generateAccessToken(User user) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", user.getRoles())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenResponse generate(
            Long userId,
            Set<Role> roles,
            Set<Permission> permissions) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

         Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        return new TokenResponse("abc","cdf",30);
    }
}
