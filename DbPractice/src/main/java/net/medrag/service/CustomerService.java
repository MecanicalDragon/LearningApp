package net.medrag.service;

import net.medrag.model.Customer;
import net.medrag.model.annotations.CustomerSessionQualifier;
import net.medrag.repo.api.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
@Service
public class CustomerService {

    @Autowired
    @CustomerSessionQualifier
    private CustomerRepo customerRepo;

    public List<Customer> getAll(){
        return customerRepo.findAll();
    }

    public Customer getById(long id){
        return customerRepo.findById(id);
    }

    public Customer save(Customer customer){
        return customerRepo.addNew(customer);
    }
}
