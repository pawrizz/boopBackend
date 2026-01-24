package boop.auth.security;

import boop.common.security.Permission;
import boop.common.security.Role;
import boop.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import boop.auth.api.dto.TokenResponse;
import boop.auth.token.RefreshTokenService;

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
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenResponse generateAccessToken(User user) {

    return new TokenResponse(getAccessToken(user), null);
    }

    private String getAccessToken(User user) 
    {
        
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", user.getRoles())
                .claim("permissions", user.getPermissions())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    
        return accessToken;

    }

    public TokenResponse generateRollingToken(User user) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        String accessToken = getAccessToken(user);
        String refreshToken = getRollingToken(user,expiryMs);
        refreshTokenService.create(user.getId(),refreshToken);
        return new TokenResponse(accessToken,refreshToken);
    }

    private String getRollingToken(User user,long expiryMs)
    {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    
        return accessToken;
    }
}
