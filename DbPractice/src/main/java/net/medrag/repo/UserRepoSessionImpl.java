package net.medrag.repo;

import lombok.val;
import net.medrag.model.User;
import net.medrag.repo.api.UserRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
@Repository
public class UserRepoSessionImpl<U extends User> implements UserRepo<User> {

    @Autowired
    private SessionFactory sessionFactory;

//    @Transactional
    public Long addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(user);
    }

    @Transactional
    public User getUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(User.class, id);
    }

    @Transactional
    public User test(User user) {
        Session session = sessionFactory.getCurrentSession();
        val id = (Long) session.save(user);
        user.setId(id);
        return user;
    }

    @Transactional
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    @Transactional
    public Long getUsersBySurname(String surname) {
        return 1L;
    }
}
