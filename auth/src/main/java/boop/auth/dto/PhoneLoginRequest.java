package boop.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneLoginRequest(
        @NotBlank
        @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
        String phone

) {}
