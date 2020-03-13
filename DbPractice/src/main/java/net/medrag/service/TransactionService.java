package net.medrag.service;

import net.medrag.model.User;
import net.medrag.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@author} Stanislav Tretyakov
 * 28.02.2020
 */
@Service
public class TransactionService {

    @Autowired
    @Qualifier("userRepoSessionImpl")
    private UserRepo<User> userRepo;

    public String addUserInTransaction(String name) {
        try {
            return addUserInTransactionNested(name).toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * Borisov lied! This does not work!
     */
    @Transactional
    public Long addUserInTransactionNested(String name) {

        System.out.println("PUT USER TRANSACTION REQUEST: " + name);
        User u = new User();
        u.setName(name);
        Long id = userRepo.addUser(u);
        System.out.println(id);

        if (Math.random() < 1) {
            throw new RuntimeException("We launched a transaction within the same component.");
        }
        return id;
    }
}
