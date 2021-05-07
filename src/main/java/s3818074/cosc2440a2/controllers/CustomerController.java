package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074.cosc2440a2.models.Customer;
import s3818074.cosc2440a2.services.AbstractService;
import s3818074.cosc2440a2.services.CustomerService;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController extends AbstractController<Customer, UUID> {
    @Autowired
    public CustomerController(CustomerService service) {
        super(service);
    }
}
