package net.medrag.repo.api;

import net.medrag.model.User;

import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
public interface UserRepo<U extends User> {
    Long addUser(U user);
    U getUser(Long id);
    U test(User user);
    List<U>getAll();
}
