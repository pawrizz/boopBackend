package boop.auth.api.dto;

public record LoginRequest(
        String phone,
        String email,
        String password,
        String otp
) {}
