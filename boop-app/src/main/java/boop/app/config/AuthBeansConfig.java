package boop.app.config;

import boop.auth.security.JwtAuthenticationFilter;
import boop.auth.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeansConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        // You manually "wire" the dependencies here
        return new JwtAuthenticationFilter(tokenProvider);
    }
}
