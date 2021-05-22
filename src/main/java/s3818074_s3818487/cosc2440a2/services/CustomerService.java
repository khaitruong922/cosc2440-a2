package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CustomerService extends AbstractService<Customer, UUID> {

    @Autowired
    public CustomerService(CustomerRepository repo) {
        super(repo);
    }

    @Override
    public Customer updateById(Customer updatedCustomer, UUID id) {
        Optional<Customer> customerOptional = repo.findById(id);
        if (customerOptional.isEmpty()) throw new RuntimeException("Customer not found");
        Customer customer = customerOptional.get();
        customer.setName(Optional.ofNullable(updatedCustomer.getName()).orElse(customer.getName()));
        customer.setAddress(Optional.ofNullable(updatedCustomer.getAddress()).orElse(customer.getAddress()));
        customer.setFax(Optional.ofNullable(updatedCustomer.getPhone()).orElse(customer.getPhone()));
        customer.setEmail(Optional.ofNullable(updatedCustomer.getEmail()).orElse(customer.getEmail()));
        customer.setContactPerson(Optional.ofNullable(updatedCustomer.getContactPerson()).orElse(customer.getContactPerson()));
        return customer;
    }
}
