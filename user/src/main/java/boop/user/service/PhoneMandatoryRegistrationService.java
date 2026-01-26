package boop.user.service;

import boop.user.api.dto.PhoneMandatoryRegistrationRequest;
import boop.user.domain.entity.User;
import boop.user.domain.repo.UserRepository;
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
        user.setPassword(req.password());

        return repo.save(user);
    }
}
