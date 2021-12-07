package net.medrag.microservices.misc.rest;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.misc.rest.model.Seniority;
import net.medrag.microservices.misc.rest.model.UserRepo;
import net.medrag.microservices.misc.rest.model.UserV2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.medrag.microservices.misc.rest.config.ProtoMessageConverter.VND_MEDRAG_PROTO;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@RestController
@RequestMapping("/converter")
@RequiredArgsConstructor
public class ConverterController {

    private final UserRepo userRepo;

    @PostMapping(value = "/seniority")
    public ResponseEntity<Void> registerSeniority(@RequestParam Seniority seniority) {
        System.out.println("Seniority accepted: " + seniority);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Void> addUser(@RequestParam UserV2 user) {
        System.out.println("User v2 accepted: " + user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user", consumes = VND_MEDRAG_PROTO, produces = VND_MEDRAG_PROTO)
    public UserV2 addUserProto(@RequestBody UserV2 user) {
        System.out.println("ProtoUser v2 accepted: ");
        System.out.print(user.getName());
        return userRepo.getUser2();
    }
}
