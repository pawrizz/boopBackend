package boop.user.api.dto;


public record GenericRegistrationRequest(
        String phone,
        String email,
        String password
) {}
