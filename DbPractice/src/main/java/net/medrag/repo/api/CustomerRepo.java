package net.medrag.repo.api;

import net.medrag.model.Customer;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
public interface CustomerRepo {
    List<Customer> findAll();
    Customer findById(Long id);
    Customer addNew(Customer customer);
}
