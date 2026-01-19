package boop.auth.api.dto;

import jakarta.validation.constraints.NotNull;

public record OtpVerifyRequest(
        @NotNull
        String phone,
        @NotNull
        String otp) {
}
