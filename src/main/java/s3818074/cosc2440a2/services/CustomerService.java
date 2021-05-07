package s3818074.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import s3818074.cosc2440a2.models.Customer;
import s3818074.cosc2440a2.repositories.CustomerRepository;

import java.util.UUID;

@Service
public class CustomerService extends AbstractService<Customer, UUID> {

    @Autowired
    public CustomerService(CustomerRepository repo) {
        super(repo);
    }
}
