package net.medrag.repo;

import net.medrag.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
@Repository
public class UserRepoImpl<U extends User> implements UserRepo<User> {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Long addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(user);
    }

    @Transactional
    public User getUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(User.class, id);
    }
}
