package boop.auth.api;

import boop.auth.api.dto.LoginRequest;
import boop.auth.api.dto.OtpVerifyRequest;
import boop.auth.api.dto.PhoneRequest;
import boop.auth.api.dto.TokenResponse;
import boop.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Pet Owner
    @PostMapping("/owner/token")
    public void requestOtp(@RequestBody PhoneRequest req) {
        authService.loginWithPhone(req.phone());
    }

    @PostMapping("/owner/token/verify")
    public TokenResponse verifyOtp(@RequestBody OtpVerifyRequest req) {
        return authService.verifyOtpAndLogin(req.phone(), req.otp());
    }

    // Doctor / Supplier / Admin
//    @PostMapping("/login/password")
//    public TokenResponse loginWithPassword(@RequestBody LoginRequest req) {
//        return authService.loginWithPassword(req.email(), req.password());
//    }
}
