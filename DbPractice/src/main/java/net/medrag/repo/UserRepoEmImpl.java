package net.medrag.repo;

import net.medrag.model.User;
import net.medrag.repo.api.UserRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 19.02.2020
 */
@Repository
public class UserRepoEmImpl<U extends User> implements UserRepo<User> {

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

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("SELECT a FROM User a", User.class).getResultList();
    }
}
