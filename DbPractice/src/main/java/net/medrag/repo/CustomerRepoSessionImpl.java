package net.medrag.repo;

import net.medrag.model.Customer;
import net.medrag.repo.api.CustomerRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
@Repository
public class CustomerRepoSessionImpl implements CustomerRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> findAll() {
        Session session = sessionFactory.openSession();
        Query<Customer> getAll = session.createQuery("select c from Customer c", Customer.class);
        return getAll.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Customer.class, id);
    }

    @Override
    public Customer addNew(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(customer);
        customer.setId(id);
        return customer;
    }
}
