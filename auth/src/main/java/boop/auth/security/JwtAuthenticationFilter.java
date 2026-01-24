package boop.auth.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwt;

    public JwtAuthenticationFilter(JwtTokenProvider jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            // 1. Parse the token
            Claims claims = jwt.parse(header.substring(7));

            //2. Verify if its ACCESS token
            if (!"ACCESS".equals(claims.get("type"))) {
                throw new SecurityException("Invalid token type");
            }

            // 2. Extract your specific info
            String userId = claims.getSubject(); // This is your UUID
            String role = claims.get("role", String.class);

            // 3. Set the 'Authorized' state in Spring
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var authority = new SimpleGrantedAuthority(role);
                var authToken = new UsernamePasswordAuthenticationToken(userId, null, List.of(authority));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        chain.doFilter(request, response);
    }
}
