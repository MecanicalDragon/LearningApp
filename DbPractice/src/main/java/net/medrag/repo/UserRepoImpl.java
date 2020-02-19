package net.medrag.repo;

import lombok.val;
import net.medrag.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public User test(User user) {
        Session session = sessionFactory.getCurrentSession();
        val id = (Long)session.save(user);
        user.setId(id);
        return user;
    }

    @Transactional
    public Long getUsersBySurname(String surname) {
        return 1L;
    }
}
