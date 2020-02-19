package net.medrag.controller;

import net.medrag.model.User;
import net.medrag.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userRepoImpl2")
    private UserRepo<User> userRepo;

    @GetMapping("/get")
    public User getUser(@RequestParam Long id){
        System.out.println("GET USER REQUEST: " + id);
        User user = userRepo.getUser(id);
        System.out.println(user.getId());
        System.out.println(user.getName());
        return user;
    }

    @GetMapping("/add")
    public Long addUser(@RequestParam String name){
        System.out.println("PUT USER REQUEST: " + name);
        User u = new User();
        u.setName(name);
        Long id = userRepo.addUser(u);
        System.out.println(id);
        return id;
    }
}
