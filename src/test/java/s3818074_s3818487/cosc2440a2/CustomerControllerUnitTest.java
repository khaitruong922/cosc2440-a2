package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import s3818074_s3818487.cosc2440a2.controllers.CustomerController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;
import s3818074_s3818487.cosc2440a2.services.CustomerService;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

    @MockBean
    private SalesInvoiceRepository salesInvoiceRepository;

    @Autowired
    @InjectMocks
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
    class Search_API {
        @Test
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
        @DisplayName("[GET] Search by multiple params")
        public void searchByMultipleParamsTest() throws Exception {
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

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Other {
        @Test
        @DisplayName("[GET] Get revenue")
        public void getRevenueTest() throws Exception {
            List<Customer> mockCustomers = populateListOfData();
            Mockito.when(repository.findAll()).thenReturn(mockCustomers);
            Date startDate = DateUtils.parse("2020-01-01");
            Date endDate = DateUtils.parse("2020-05-31");
            List<SalesInvoice> mockSalesInvoices = Arrays.asList(
                    new SalesInvoice(uuid(), DateUtils.parse("2020-01-01"), new Staff(), mockCustomers.get(0), Collections.emptyList(), 20.0),
                    new SalesInvoice(uuid(), DateUtils.parse("2020-04-01"), new Staff(), mockCustomers.get(0), Collections.emptyList(), 40.0),
                    new SalesInvoice(uuid(), DateUtils.parse("2020-06-01"), new Staff(), mockCustomers.get(0), Collections.emptyList(), 25.0),
                    new SalesInvoice(uuid(), DateUtils.parse("2020-03-01"), new Staff(), mockCustomers.get(1), Collections.emptyList(), 30.0)
            );
            Mockito.when(salesInvoiceRepository.findAll()).thenReturn(mockSalesInvoices);
            // Without date
            Assertions.assertEquals(service.getRevenue(mockCustomers.get(0).getId(), null, null), 85.0);
            // With start and end date
            Assertions.assertEquals(service.getRevenue(mockCustomers.get(0).getId(), startDate, endDate), 60.0);
            // With another customer
            Assertions.assertEquals(service.getRevenue(mockCustomers.get(1).getId(), startDate, endDate), 30.0);
            // With a customer without sales invoices
            Assertions.assertEquals(service.getRevenue(mockCustomers.get(2).getId(), startDate, endDate), 0);

            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}



