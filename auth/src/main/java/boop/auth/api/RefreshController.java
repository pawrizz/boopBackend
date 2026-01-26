package boop.auth.api;

import boop.auth.dto.PhoneVerifyRequest;
import boop.auth.dto.RefreshTokenVerify;
import boop.auth.dto.TokenResponse;
import boop.auth.service.AuthService;
import boop.auth.service.RefreshTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RefreshController {

    private final RefreshTokenService refreshService;

    public RefreshController(RefreshTokenService refreshService) {
        this.refreshService = refreshService;
    }

    @PostMapping("/refresh")
    public TokenResponse validateRefresh(@RequestBody RefreshTokenVerify req) {
        return refreshService.verifyAndRotate(req.refreshToken);
    }
}
