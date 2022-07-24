package net.medrag.microservices.security.keycloack;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Stanislav Tretyakov
 * 12.12.2021
 */
@RestController
public class KeycloakController {

    @GetMapping("/user")
    public String userStuff(Principal principal) {
        return "user stuff is admitted for " + Optional.ofNullable(principal).map(Principal::getName).orElse("anon");
    }

    @GetMapping("/admin")
    public String adminStuff(Principal principal) {
        return "admin stuff is admitted for " + Optional.ofNullable(principal).map(Principal::getName).orElse("anon");
    }

    @GetMapping("/free")
    public String freeStuff(Principal principal) {
        return "free stuff is admitted for " + Optional.ofNullable(principal).map(Principal::getName).orElse("anon");
    }

    @GetMapping("/me")
    public String getMe() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
