package boop.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenVerify {
    @NotBlank
    public String refreshToken;
}
