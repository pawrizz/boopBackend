package boop.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenResponse(
        @NotBlank
        String accessToken,
        String refreshToken // null for non-pet-owner
) {}
