package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.CustomerController;
import s3818074_s3818487.cosc2440a2.models.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerControllerUnitTest extends AbstractUnitTest<Customer>{
    @InjectMocks
    private CustomerController controller;

    public CustomerControllerUnitTest() {
        super("customers");
    }

    @BeforeEach
    public void init(){
        setUp(controller);
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



