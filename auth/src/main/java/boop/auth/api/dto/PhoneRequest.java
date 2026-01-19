package boop.auth.api.dto;

import jakarta.validation.constraints.NotNull;

public record PhoneRequest(
        @NotNull
        String phone)
{}
