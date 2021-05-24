package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
    public void init() {
        setUp(controller, service, repository);
    }

    @Override
    protected Customer populateData() {
        return new Customer(uuid(), "Tin Provider", "123 ABC",
                "0909090888", "123", "admin@email.com", "Chung Quan Tin");
    }

    @Override
    protected List<Customer> populateListOfData() {
        return Arrays.asList(
                new Customer(uuid(), "Tin Cus", "123 ABC",
                        "0909090888", "123", "admin@email.com", "Chung Quan Tin"),
                new Customer(uuid(), "Khai 1", "19 Earth",
                        "0908321238", "123", "admin@email.com", "Chung Quan Tin"),
                new Customer(uuid(), "Khai 2", "19 Earth",
                        "0908321238", "123", "admin@email.com", "Chung Quan Tin"),
                new Customer(uuid(), "Khai 3", "20 Earth",
                        "0908321238", "123", "admin@email.com", "Chung Quan Tin")
        );
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Customer");
    }

    @Test
    @Order(2)
    @DisplayName("[GET] Search by name")
    public void searchByNameTest() {
        Mockito.when(repository.findAll()).thenReturn(populateListOfData());
        String name = "tin";
        List<Customer> customersWithName = controller.search(name, null, null, null);
        Assertions.assertEquals(customersWithName.size(), 1);
    }
}



