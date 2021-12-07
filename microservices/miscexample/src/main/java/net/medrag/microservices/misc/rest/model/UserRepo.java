package net.medrag.microservices.misc.rest.model;

import org.springframework.stereotype.Component;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@Component
public class UserRepo {

    public User getUser() {
        return new User(
                "Stanislav",
                "Germanovich",
                "Voronkov",
                "89876543210",
                "Peterburg",
                "example@example.com"
        );
    }

    public UserV2 getUser2() {
        return new UserV2(
                "Stanislav",
                "Tretyakov",
                "89876543210",
                Seniority.OVERLORD
        );
    }
}
