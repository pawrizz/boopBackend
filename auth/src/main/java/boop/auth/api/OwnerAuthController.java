package boop.auth.api;

import boop.auth.api.dto.PhoneLoginRequest;
import boop.auth.api.dto.PhoneVerifyRequest;
import boop.auth.api.dto.TokenResponse;
import boop.auth.service.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/owner")
public class OwnerAuthController {

    private final AuthService authService;

    public OwnerAuthController(AuthService authService) {
        this.authService = authService;
    }

    // Pet Owner
    @PostMapping("/login")
    public void requestOtp(@RequestBody PhoneLoginRequest req) {
        authService.loginWithPhone(req.phone());
    }

    @PostMapping("/verify")
    public TokenResponse verifyOtp(@RequestBody PhoneVerifyRequest req) {
        return authService.verifyOtpAndLogin(req.phone(), req.otp());
    }

    @PostMapping("/refresh")
    public TokenResponse validateRefresh(@RequestBody PhoneVerifyRequest req) {
        return authService.verifyOtpAndLogin(req.phone(), req.otp());
    }

    @PostMapping("/logout")
    public TokenResponse logoutUser(@AuthenticationPrincipal String publicId) {
        return authService.verifyOtpAndLogin(publicId, null);
    }



}
