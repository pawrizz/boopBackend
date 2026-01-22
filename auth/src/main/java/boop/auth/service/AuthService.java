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

            user = userService.authenticateByPhone(req.phone(), req.password());

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

    public void loginWithPhone(String phone)
    {
        userService.authenticatePetOwner(phone);
    }

    public TokenResponse verifyOtpAndLogin(String phone, String otp)
    {
        if(otpService.verify(phone,otp))
        {
            User user = userService.authenticatePetOwner(phone);

            return new TokenResponse(jwt.generate(
                    user.getId(),
                    user.getRoles(),
                    user.getPermissions()
            ));
        }

        return null;
    }
}
