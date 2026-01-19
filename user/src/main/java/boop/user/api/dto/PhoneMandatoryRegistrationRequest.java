package boop.user.api.dto;

public record PhoneMandatoryRegistrationRequest(
        String phone,
        String email,
        String password
) {}
