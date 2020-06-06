package net.medrag.repo;

import net.medrag.model.Customer;
import net.medrag.repo.api.CustomerRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
@Repository
public class CustomerRepoEmImpl implements CustomerRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public Customer addNew(Customer customer) {
        return null;
    }
}
