package net.medrag.controller;

import net.medrag.model.User;
import net.medrag.repo.UserRepo;
import net.medrag.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userRepoSessionImpl")
    private UserRepo<User> userRepo;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getAll")
    public List<User> getAll() {
        Lock lock = new StampedLock();
        return userRepo.getAll();
    }

    @GetMapping("/get")
    public User getUser(@RequestParam Long id) {
        System.out.println("GET USER REQUEST: " + id);
        User user = userRepo.getUser(id);
        System.out.println(user.getId());
        System.out.println(user.getName());
        return user;
    }

    @GetMapping("/add")
    public Long addUser(@RequestParam String name) {
        System.out.println("PUT USER REQUEST: " + name);
        User u = new User();
        u.setName(name);
        Long id = userRepo.addUser(u);
        System.out.println(id);
        return id;
    }

    @GetMapping("/addT")
    public String addUserInT(@RequestParam String name) {
        return transactionService.addUserInTransaction(name);
    }


    @GetMapping("/test")
    public User test(@RequestParam String x) {
        System.out.println("TEST REQUEST: " + x);
        User user = new User();
        user.setName("Stanislav");
        user.setSurname("Tretyakov");
        user.setAge(31);
        user = userRepo.test(user);
        System.out.println(user);
        return user;
    }
}
