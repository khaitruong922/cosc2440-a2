package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074.cosc2440a2.models.Customer;
import s3818074.cosc2440a2.services.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController implements Controller<Customer> {
    @Autowired
    protected CustomerService service;

    @Override
    public List<Customer> getAll() {
        return service.getAll();
    }

    @Override
    public Customer getById(UUID id) {
        return service.getById(id);
    }

    @Override
    public Customer add(Customer customer) {
        return service.add(customer);
    }

    @Override
    public HttpStatus deleteById(UUID id) {
        return service.deleteById(id);
    }

    @Override
    public HttpStatus deleteAll() {
        return service.deleteAll();
    }
}
