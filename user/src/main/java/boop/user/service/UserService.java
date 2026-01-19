package boop.user.service;

import boop.user.domain.User;
import boop.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo,
                       PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // ---------- EMAIL + PASSWORD ----------

    public User authenticateByEmail(String email, String rawPassword) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (user.getPassword() == null ||
                !encoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    // ---------- PHONE + OTP (STUB FOR NOW) ----------

    public User authenticateByPhone(String phone, String otp) {

        User user = repo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Invalid phone number"));

        // OTP validation will be implemented later
        // For now, we assume OTP is valid if user exists

        return user;
    }
}
