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
import s3818074_s3818487.cosc2440a2.controllers.StaffController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;
import s3818074_s3818487.cosc2440a2.services.StaffService;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StaffControllerUnitTest extends AbstractControllerUnitTest<Staff> {
    public StaffControllerUnitTest() {
        super("staffs");
    }

    @InjectMocks
    @Autowired
    private StaffController controller;

    @MockBean
    protected StaffRepository repository;

    @MockBean
    private SalesInvoiceRepository salesInvoiceRepository;

    @InjectMocks
    @Autowired
    private StaffService service;

    @BeforeEach
    public void init() {
        setup(controller, service, repository);
    }

    @Override
    protected Staff populateData() {
        return new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
    }

    @Override
    protected List<Staff> populateListOfData() {
        return Arrays.asList(
                new Staff(uuid(), "Tin", "123 ABC",
                        "0909090888", "admin@email.com", "Chung Quan Tin"),
                new Staff(uuid(), "Khai 1", "19 Earth",
                        "0908321238", "admin@email.com", "Chung Quan Tin"),
                new Staff(uuid(), "Khai 2", "19 Earth",
                        "0908321238", "admin@email.com", "Chung Quan Tin"),
                new Staff(uuid(), "Khai 3", "20 Earth",
                        "0908321238", "admin@email.com", "Chung Quan Tin")
        );
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Staff");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Additional_API {
        @Test
        @DisplayName("[GET] Get revenue")
        public void getRevenueTest() throws Exception {
            List<Staff> mockStaffs = populateListOfData();
            Mockito.when(repository.findAll()).thenReturn(mockStaffs);
            Date startDate = DateUtils.parse("2020-01-01");
            Date endDate = DateUtils.parse("2020-05-31");
            List<SalesInvoice> mockSalesInvoices = Arrays.asList(
                    new SalesInvoice(uuid(), DateUtils.parse("2020-01-01"), mockStaffs.get(0), new Customer(), Collections.emptyList(), 20.0),
                    new SalesInvoice(uuid(), DateUtils.parse("2020-04-01"), mockStaffs.get(0), new Customer(), Collections.emptyList(), 40.0),
                    new SalesInvoice(uuid(), DateUtils.parse("2020-06-01"), mockStaffs.get(0), new Customer(), Collections.emptyList(), 25.0),
                    new SalesInvoice(uuid(), DateUtils.parse("2020-03-01"), mockStaffs.get(1), new Customer(), Collections.emptyList(), 30.0)
            );
            Mockito.when(salesInvoiceRepository.findAll()).thenReturn(mockSalesInvoices);
            // Without date
            Assertions.assertEquals(controller.getRevenue(mockStaffs.get(0).getId(), null, null), 85.0);
            // With start and end date
            Assertions.assertEquals(controller.getRevenue(mockStaffs.get(0).getId(), startDate, endDate), 60.0);
            // With another staff
            Assertions.assertEquals(controller.getRevenue(mockStaffs.get(1).getId(), startDate, endDate), 30.0);
            // With a staff without sales invoices
            Assertions.assertEquals(controller.getRevenue(mockStaffs.get(2).getId(), startDate, endDate), 0);

            mockMvc.perform(get("/" + endpoint + "/" + mockStaffs.get(0).getId() + "/revenue").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}
