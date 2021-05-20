package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class CustomerService extends AbstractService<Customer, UUID> {

    @Autowired
    public CustomerService(CustomerRepository repo, SalesInvoiceRepository salesInvoiceRepository) {
        super(repo);
    }
}
