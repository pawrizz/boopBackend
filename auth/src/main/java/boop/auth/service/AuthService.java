package boop.auth.service;

import boop.auth.api.dto.LoginRequest;
import boop.auth.api.dto.TokenResponse;
import boop.auth.otp.OtpService;
import boop.auth.security.JwtTokenProvider;
import boop.user.domain.User;
import boop.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwt;
    private final OtpService otpService;

    public AuthService(UserService userService,
                       JwtTokenProvider jwt,
                       OtpService otpService) {
        this.userService = userService;
        this.jwt = jwt;
        this.otpService = otpService;
    }

    public TokenResponse login(LoginRequest req) {

        User user;

        if (req.phone() != null) {

            if (!otpService.verify(req.phone(), req.otp())) {
                throw new RuntimeException("Invalid OTP");
            }

            user = userService.authenticateByPhone(req.phone(), req.otp());

        } else if (req.email() != null) {

            user = userService.authenticateByEmail(
                    req.email(), req.password());

        } else {
            throw new IllegalArgumentException("Invalid login request");
        }

        return new TokenResponse(jwt.generate(
                user.getId(),
                user.getRoles(),
                user.getPermissions()
        ));
    }
}
