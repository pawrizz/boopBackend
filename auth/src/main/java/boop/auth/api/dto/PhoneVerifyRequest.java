package boop.auth.api.dto;

import jakarta.validation.constraints.NotNull;

public record PhoneVerifyRequest(
        @NotNull
        String phone,
        @NotNull
        String otp) {
}
