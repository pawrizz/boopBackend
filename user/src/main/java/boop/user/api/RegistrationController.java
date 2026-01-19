package boop.user.api;

import boop.common.security.Permission;
import boop.common.security.Role;
import boop.user.api.dto.GenericRegistrationRequest;
import boop.user.api.dto.PhoneMandatoryRegistrationRequest;
import boop.user.application.GenericRegistrationService;
import boop.user.application.PhoneMandatoryRegistrationService;
import boop.user.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final GenericRegistrationService genericService;
    private final PhoneMandatoryRegistrationService phoneService;

    public RegistrationController(GenericRegistrationService genericService,
                                  PhoneMandatoryRegistrationService phoneService) {
        this.genericService = genericService;
        this.phoneService = phoneService;
    }

    @PostMapping("/generic")
    public void registerGeneric(@RequestBody GenericRegistrationRequest req) {

        User user = genericService.register(req);

        user.setRoles(Set.of(Role.ROLE_DOCTOR));
        user.setPermissions(Set.of(Permission.DOCTOR_BASIC));
    }

    @PostMapping("/phone-mandatory")
    public void registerPhoneMandatory(@RequestBody PhoneMandatoryRegistrationRequest req) {

        User user = phoneService.register(req);

        user.setRoles(Set.of(Role.ROLE_PET_OWNER));
        user.setPermissions(Set.of(Permission.PET_OWNER_FULL));
    }
}
