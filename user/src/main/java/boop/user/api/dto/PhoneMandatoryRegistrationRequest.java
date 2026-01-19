package boop.user.api.dto;

import jakarta.validation.constraints.NotBlank;

public record PhoneMandatoryRegistrationRequest(
        @NotBlank(message = "Phone number is mandatory")
        String phone,
        String otp,
        String password
) {}
