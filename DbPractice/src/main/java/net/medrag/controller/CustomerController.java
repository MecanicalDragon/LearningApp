package net.medrag.controller;

import net.medrag.model.Customer;
import net.medrag.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/findById")
    public Customer findById(long id) {
        return customerService.getById(id);
    }

    @GetMapping("/getAll")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @PostMapping("/add")
    public Customer addNew(Customer customer) {
        return customerService.save(customer);
    }
}
