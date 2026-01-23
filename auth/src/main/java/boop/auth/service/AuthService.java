package boop.auth.service;

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

    public void loginWithPhone(String phone)
    {
        userService.authenticatePetOwner(phone);
    }

    public TokenResponse verifyOtpAndLogin(String phone, String otp)
    {
        if(otpService.verify(phone,otp))
        {
            User user = userService.getPetOwnerUser(phone);
            return jwt.generateRollingToken(user);   
        }
        
        return null;
    }
}
