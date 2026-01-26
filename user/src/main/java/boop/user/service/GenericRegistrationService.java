package boop.user.service;

import boop.user.api.dto.GenericRegistrationRequest;
import boop.user.domain.entity.User;
import boop.user.domain.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GenericRegistrationService {

    private final UserRepository repo;

    public GenericRegistrationService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(GenericRegistrationRequest req) {

        if ((req.phone() == null || req.phone().isBlank()) &&
                (req.email() == null || req.email().isBlank())) {
            throw new IllegalArgumentException(
                    "Either phone or email is required");
        }

        User user = new User();
        user.setPhone(req.phone());
        user.setEmail(req.email());

        return repo.save(user);
    }
}
