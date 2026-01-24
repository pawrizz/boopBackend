package boop.user.service;

import boop.common.security.Permission;
import boop.common.security.Role;
import boop.user.domain.User;
import boop.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

    public User authenticateByPhone(String phone, String rawPassword) {

        User user = repo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (user.getPassword() == null ||
                !encoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    @Transactional
    public void authenticatePetOwner(String phone) {

        repo.findByPhone(phone)
                .orElseGet(() -> {
                    User user = new User();
                    user.setPhone(phone);
                    user.setPassword(null); // OTP-only user
                    user.setRoles(Set.of(Role.ROLE_PET_OWNER));
                    user.setPermissions(Set.of(Permission.PET_OWNER_FULL));
                    user.setProvider("PHONE_OTP");
                    return repo.save(user);
                });
    }

    public User getPetOwnerUser(String phone) {

        return repo.findByPhone(phone).orElseThrow(() ->
                new RuntimeException("User not found"));
    }
}
