package boop.auth.api.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken, // null for non-pet-owner
        long expiresInSeconds
) {}
