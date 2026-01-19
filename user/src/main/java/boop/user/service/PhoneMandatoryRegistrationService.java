package boop.user.application;

import boop.user.api.dto.PhoneMandatoryRegistrationRequest;
import boop.user.domain.User;
import boop.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PhoneMandatoryRegistrationService {

    private final UserRepository repo;

    public PhoneMandatoryRegistrationService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(PhoneMandatoryRegistrationRequest req) {

        if (req.phone() == null || req.phone().isBlank()) {
            throw new IllegalArgumentException(
                    "Phone number is mandatory");
        }

        User user = new User();
        user.setPhone(req.phone());
        user.setEmail(req.email());

        return repo.save(user);
    }
}
