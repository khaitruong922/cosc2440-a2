package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import s3818074_s3818487.cosc2440a2.controllers.SalesInvoiceController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;
import s3818074_s3818487.cosc2440a2.services.SalesInvoiceService;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SalesInvoiceUnitTest extends AbstractUnitTest<SalesInvoice> {
    @InjectMocks
    @Autowired
    private SalesInvoiceController controller;

    public SalesInvoiceUnitTest() {
        super("sales-invoices");
    }

    @MockBean
    protected SalesInvoiceRepository repository;

    @MockBean
    protected CustomerRepository customerRepository;

    @MockBean
    protected StaffRepository staffRepository;

    @MockBean
    protected SalesDetailRepository salesDetailRepository;

    @Autowired
    protected SalesInvoiceService service;

    @BeforeEach
    public void init() {
        setUp(controller, service, repository);
    }

    @Override
    protected SalesInvoice populateData() {
        Category category = new Category(uuid(), "bike");
        Product product = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", category, 25.5);

        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        Mockito.when(staffRepository.findById(staff.getId())).thenReturn(Optional.of(staff));

        Customer customer = new Customer(uuid(), "Tin Customer", "123 ABC",
                "0909090888", "123", "admin@email.com", "Chung Quan Tin");
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        SalesDetail sd1 = new SalesDetail(uuid(), product, 100, 15);
        Mockito.when(salesDetailRepository.findById(sd1.getId())).thenReturn(Optional.of(sd1));
        SalesDetail sd2 = new SalesDetail(uuid(), product, 100, 15);
        Mockito.when(salesDetailRepository.findById(sd2.getId())).thenReturn(Optional.of(sd2));

        return new SalesInvoice(uuid(), new Date(), staff, customer, Arrays.asList(
                sd1, sd2
        ), 3000.0);
    }

    @Override
    protected List<SalesInvoice> populateListOfData() {
        Category category = new Category(uuid(), "bike");
        Product product = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", category, 25.5);
        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        Customer customer = new Customer(uuid(), "Tin Customer", "123 ABC",
                "0909090888", "123", "admin@email.com", "Chung Quan Tin");
        return Arrays.asList(
                new SalesInvoice(uuid(), DateUtils.parse("2020-01-01"), staff, customer, Arrays.asList(
                        new SalesDetail(uuid(), product, 100, 15),
                        new SalesDetail(uuid(), product, 100, 15)
                ), 3000.0),
                new SalesInvoice(uuid(), DateUtils.parse("2020-02-01"), staff, customer, Arrays.asList(
                        new SalesDetail(uuid(), product, 100, 15),
                        new SalesDetail(uuid(), product, 100, 15)
                ), 3000.0),
                new SalesInvoice(uuid(), DateUtils.parse("2020-03-01"), staff, customer, Arrays.asList(
                        new SalesDetail(uuid(), product, 100, 15),
                        new SalesDetail(uuid(), product, 100, 15)
                ), 3000.0),
                new SalesInvoice(uuid(), DateUtils.parse("2020-04-01"), staff, customer, Arrays.asList(
                        new SalesDetail(uuid(), product, 100, 15),
                        new SalesDetail(uuid(), product, 100, 15)
                ), 3000.0)
        );
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Sales invoice");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Element_Not_Found {
        @Test
        @DisplayName("[POST][ERROR] Customer not found!")
        void addTestThrowCustomerNotFound() {
            try {
                SalesInvoice data = populateData();

                data.setCustomer(new Customer());

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/sales-invoices")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e) {
                Assertions.assertEquals(e.getMessage(), "Customer not found!");
            }
        }

        @Test
        @DisplayName("[POST][ERROR] Staff not found!")
        void addTestThrowStaffNotFound() {
            try {
                SalesInvoice data = populateData();

                data.setStaff(new Staff());

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/sales-invoices")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e) {
                Assertions.assertEquals(e.getMessage(), "Staff not found!");
            }
        }

        @Test
        @DisplayName("[POST][ERROR] Sales detail not found!")
        void addTestThrowSalesDetailNotFound() {
            try {
                SalesInvoice data = populateData();

                data.setSalesDetails(Collections.singletonList(new SalesDetail()));

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/sales-invoices")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e) {
                Assertions.assertEquals(e.getMessage(), "Sales detail not found!");
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Search_API {
        @Test
        @DisplayName("[GET] Filter by date")
        public void filterByDateTest() throws Exception {
            Mockito.when(repository.findAll()).thenReturn(populateListOfData());
            int expectedCount = 3;
            Date startDate = DateUtils.parse("2020-01-01");
            Date endDate = DateUtils.parse("2020-03-01");
            Assertions.assertEquals(controller.search(startDate, endDate, null, null, null).size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("start", DateUtils.format(startDate))
                    .param("end", DateUtils.format(endDate)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));

        }
    }
}
