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
import org.springframework.http.MediaType;
import s3818074_s3818487.cosc2440a2.controllers.CustomerController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.services.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                new Customer(uuid(), "Tin", "123 ABC",
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

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Search_API{
        @Test
        @Order(2)
        @DisplayName("[GET] Search by name")
        public void searchByNameTest() throws Exception {
            Mockito.when(repository.findAll()).thenReturn(populateListOfData());
            int expectedCount = 1;
            String name = "tin";
            List<Customer> customers = service.search(name, null, null, null);
            Assertions.assertEquals(customers.size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON).param("name", name))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));
        }

        @Test
        @Order(2)
        @DisplayName("[GET] Search by phone")
        public void searchByPhoneTest() throws Exception {
            Mockito.when(repository.findAll()).thenReturn(populateListOfData());
            int expectedCount = 3;
            String phone = "0908321238";
            List<Customer> customersWithName = service.search(null, phone, null, null);
            Assertions.assertEquals(customersWithName.size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON).param("phone", phone))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));
        }

        @Test
        @Order(2)
        @DisplayName("[GET] Search by address")
        public void searchByAddressTest() throws Exception {
            Mockito.when(repository.findAll()).thenReturn(populateListOfData());
            int expectedCount = 2;
            String address = "19 earth";
            List<Customer> customers = service.search(null, null, address, null);
            Assertions.assertEquals(customers.size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON).param("address", address))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));
        }

        @Test
        @Order(2)
        @DisplayName("[GET] Search by multiple params")
        public void searchByMultipleParams() throws Exception {
            Mockito.when(repository.findAll()).thenReturn(populateListOfData());
            int expectedCount = 1;
            String name = "Khai 2";
            String address = "19 earth";
            String phone = "0908321238";
            List<Customer> customers = service.search(name, phone, address, null);
            Assertions.assertEquals(customers.size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("name", name)
                    .param("phone", phone)
                    .param("address", address))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));
        }
    }
}



