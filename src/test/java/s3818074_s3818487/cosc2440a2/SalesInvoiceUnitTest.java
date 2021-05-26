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

    @InjectMocks
    @Autowired
    protected SalesInvoiceService service;

    @BeforeEach
    public void init() {
        setup(controller, service, repository);
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

        @Test
        @DisplayName("[GET] Filter by customer")
        public void filterByCustomerTest() throws Exception {
            List<SalesInvoice> salesInvoices = populateListOfData();
            Customer c1 = new Customer(uuid(), "Tin", "123 ABC",
                    "0909090888", "123", "admin@email.com", "Chung Quan Tin");
            Customer c2 = new Customer(uuid(), "Khai", "123 ABC",
                    "0909090888", "123", "admin@email.com", "Chung Quan Tin");
            salesInvoices.get(0).setCustomer(c1);
            salesInvoices.get(1).setCustomer(c1);
            salesInvoices.get(2).setCustomer(c1);
            salesInvoices.get(3).setCustomer(c2);

            Mockito.when(repository.findAll()).thenReturn(salesInvoices);
            int expectedCount = 3;
            Assertions.assertEquals(controller.search(null, null, null, c1.getId(), null).size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("customer", c1.getId().toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));

        }

        @Test
        @DisplayName("[GET] Filter by staff")
        public void filterByStaffTest() throws Exception {
            List<SalesInvoice> salesInvoices = populateListOfData();
            Staff s1 = new Staff(uuid(), "Tin", "123 ABC", "0909090888",
                    "admin@email.com", "Chung Quan Tin");
            Staff s2 = new Staff(uuid(), "Khai", "123 ABC", "0909090888",
                    "admin@email.com", "Chung Quan Tin");

            salesInvoices.get(0).setStaff(s1);
            salesInvoices.get(1).setStaff(s1);
            salesInvoices.get(2).setStaff(s1);
            salesInvoices.get(3).setStaff(s2);

            Mockito.when(repository.findAll()).thenReturn(salesInvoices);
            int expectedCount = 3;
            Assertions.assertEquals(controller.search(null, null, s1.getId(), null, null).size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("staff", s1.getId().toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));

        }

        @Test
        @DisplayName("[GET] Filter by multiple params")
        public void filterByMultipleParamsTest() throws Exception {
            List<SalesInvoice> salesInvoices = populateListOfData();
            Staff s1 = new Staff(uuid(), "Tin", "123 ABC", "0909090888",
                    "admin@email.com", "Chung Quan Tin");
            Customer c1 = new Customer(uuid(), "Khai", "123 ABC",
                    "0909090888", "123", "admin@email.com", "Chung Quan Tin");
            Date startDate = DateUtils.parse("2020-01-01");
            Date endDate = DateUtils.parse("2020-02-01");

            salesInvoices.get(0).setStaff(s1);
            salesInvoices.get(0).setCustomer(c1);
            salesInvoices.get(0).setDate(DateUtils.parse("2020-01-01"));

            salesInvoices.get(1).setStaff(s1);
            salesInvoices.get(1).setDate(DateUtils.parse("2020-02-01"));

            salesInvoices.get(2).setCustomer(c1);
            salesInvoices.get(2).setDate(DateUtils.parse("2020-02-01"));

            salesInvoices.get(3).setStaff(s1);
            salesInvoices.get(3).setCustomer(c1);
            salesInvoices.get(3).setDate(DateUtils.parse("2020-03-01"));


            Mockito.when(repository.findAll()).thenReturn(salesInvoices);

            // Filter by customer within a period
            int expectedCount1 = 2;
            Assertions.assertEquals(controller.search(startDate, endDate, null, c1.getId(), null).size(), expectedCount1);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("customer", c1.getId().toString())
                    .param("start", DateUtils.format(startDate))
                    .param("end", DateUtils.format(endDate)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount1)));

            // Filter by staff within a period
            int expectedCount2 = 2;
            Assertions.assertEquals(controller.search(startDate, endDate, s1.getId(), null, null).size(), expectedCount2);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("staff", s1.getId().toString())
                    .param("start", DateUtils.format(startDate))
                    .param("end", DateUtils.format(endDate)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount2)));

            // Filter by customer and staff within a period
            int expectedCount3 = 1;
            Assertions.assertEquals(controller.search(startDate, endDate, s1.getId(), c1.getId(), null).size(), expectedCount3);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("staff", s1.getId().toString())
                    .param("customer", c1.getId().toString())
                    .param("start", DateUtils.format(startDate))
                    .param("end", DateUtils.format(endDate)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount3)));


        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Additional_API {
        @Test
        @DisplayName("[GET] Get revenue")
        public void getRevenueTest() throws Exception {
            List<SalesInvoice> salesInvoices = populateListOfData();
            when(repository.findAll()).thenReturn(salesInvoices);
            Date startDate = DateUtils.parse("2020-01-01");
            Date endDate = DateUtils.parse("2020-02-01");

            Assertions.assertEquals(controller.getRevenue(null, null), 12000.0);
            Assertions.assertEquals(controller.getRevenue(startDate, endDate), 6000.0);

            mockMvc.perform(get("/" + endpoint + "/revenue").contentType(MediaType.APPLICATION_JSON)
                    .param("start", DateUtils.format(startDate))
                    .param("end", DateUtils.format(endDate)))
                    .andExpect(status().isOk());

        }
    }

}
