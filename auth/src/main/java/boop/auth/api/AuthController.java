package boop.auth.api;

import boop.auth.api.dto.PhoneLoginRequest;
import boop.auth.api.dto.PhoneVerifyRequest;
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
    @PostMapping("/petowner/login")
    public void requestOtp(@RequestBody PhoneLoginRequest req) {
        authService.loginWithPhone(req.phone());
    }

    @PostMapping("/petowner/verify")
    public TokenResponse verifyOtp(@RequestBody PhoneVerifyRequest req) {
        return authService.verifyOtpAndLogin(req.phone(), req.otp());
    }

    // @PutMapping("petowner/{userId}/updateUser")
    // public void putMethodName(@PathVariable String id, @RequestBody String entity) {
    //     //TODO: process PUT request
        
    //     return entity;
    // }

    // Doctor / Supplier / Admin
//    @PostMapping("/login/password")
//    public TokenResponse loginWithPassword(@RequestBody LoginRequest req) {
//        return authService.loginWithPassword(req.email(), req.password());
//    }


}
