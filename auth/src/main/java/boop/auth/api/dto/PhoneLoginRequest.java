package boop.auth.api.dto;

import jakarta.validation.constraints.NotNull;

public record PhoneLoginRequest(
        @NotNull
        String phone

) {}
