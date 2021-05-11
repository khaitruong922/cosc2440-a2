package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.filters.CustomerFilter;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.services.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController extends AbstractController<Customer, UUID> {

    @Autowired
    public CustomerController(CustomerService service) {
        super(service);
    }

    @GetMapping("/search")
    public List<Customer> search(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone,
                                 @RequestParam(required = false) String address) {
        return new CustomerFilter(service.getAll()).withName(name).withPhone(phone).withAddress(address).get();
    }
}