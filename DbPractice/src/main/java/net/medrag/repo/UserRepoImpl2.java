package net.medrag.repo;

import net.medrag.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@author} Stanislav Tretyakov
 * 19.02.2020
 */
@Repository
public class UserRepoImpl2<U extends User> implements UserRepo<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long addUser(User user) {
        entityManager.persist(user);
        return 1L;
    }

    @Transactional
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public User test(User user) {
        return null;
    }
}
