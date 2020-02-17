package net.medrag.repo;

import net.medrag.model.User;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
public interface UserRepo<U extends User> {
    Long addUser(U user);
    U getUser(Long id);
}
