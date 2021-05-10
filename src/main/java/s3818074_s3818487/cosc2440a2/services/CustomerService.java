package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService extends AbstractService<Customer, UUID> {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository repo) {
        super(repo);
        this.customerRepository = repo;
    }

    public List<Customer> getAllByName(String name) {
        return customerRepository.findAllByName(name);
    }

    public List<Customer> getAllByAddress(String address) {
        return customerRepository.findAllByAddress(address);
    }

    public List<Customer> getAllByPhone(String phone) {
        return customerRepository.findAllByPhone(phone);
    }
}
