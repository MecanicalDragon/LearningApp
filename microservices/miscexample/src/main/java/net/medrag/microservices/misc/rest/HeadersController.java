package net.medrag.microservices.misc.rest;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.misc.rest.model.User;
import net.medrag.microservices.misc.rest.model.UserRepo;
import net.medrag.microservices.misc.rest.model.UserV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@RestController
@RequestMapping("/headers")
@RequiredArgsConstructor
public class HeadersController {

    private static final String USER_V2 = "application/vnd.medrag.v2+json";

    private final UserRepo userRepo;

    /**
     * Standard GET endpoint.
     */
    @GetMapping("/user")
    public User getUser() {
        return userRepo.getUser();
    }

    /**
     * Endpoint of version 2 entity
     * Header `Accept` must be USER_V2 to route request here.
     */
    @GetMapping(value = "/user", produces = USER_V2)
    public UserV2 getUser2() {
        return userRepo.getUser2();
    }
}
