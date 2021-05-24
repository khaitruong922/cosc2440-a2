package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import s3818074_s3818487.cosc2440a2.controllers.CustomerController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.services.CustomerService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerControllerUnitTest extends AbstractUnitTest<Customer> {
    @Autowired
    @InjectMocks
    private CustomerController controller;

    public CustomerControllerUnitTest() {
        super("customers");
    }

    @MockBean
    protected CustomerRepository repository;

    @Autowired
    protected CustomerService service;

    @BeforeEach
    public void init(){
        setUp(controller, service, repository);
    }

    @Override
    protected Customer populateData() {
        return new Customer(uuid(),"Tin Provider", "123 ABC",
                "0909090888", "123","admin@email.com","Chung Quan Tin");
    }

    @Override
    protected List<Customer> populateListOfData() {
        return Arrays.asList(
                new Customer(uuid(),"Tin Cus", "123 ABC",
                        "0909090888", "123","admin@email.com","Chung Quan Tin"),
                new Customer(uuid(),"Tin Cus", "123 ABC",
                        "0909090888", "123","admin@email.com","Chung Quan Tin"),
                new Customer(uuid(),"Tin Cus", "123 ABC",
                        "0909090888", "123","admin@email.com","Chung Quan Tin")
        );
    }
}



