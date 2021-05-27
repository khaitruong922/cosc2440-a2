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
import s3818074_s3818487.cosc2440a2.controllers.DeliveryNoteController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.DeliveryDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.DeliveryNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;
import s3818074_s3818487.cosc2440a2.services.DeliveryNoteService;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DeliveryNoteControllerUnitTest extends AbstractControllerUnitTest<DeliveryNote> {
    @InjectMocks
    @Autowired
    private DeliveryNoteController controller;

    public DeliveryNoteControllerUnitTest() {
        super("delivery-notes");
    }

    @MockBean
    protected DeliveryNoteRepository repository;

    @MockBean
    protected StaffRepository staffRepository;

    @MockBean
    protected DeliveryDetailRepository deliveryDetailRepository;

    @BeforeEach
    public void init() {
        setup(controller, service, repository);
    }


    @InjectMocks
    @Autowired
    protected DeliveryNoteService service;

    @Override
    protected DeliveryNote populateData() {
        Category c1 = new Category(uuid(), "bike");
        Category c2 = new Category(uuid(), "book");
        Product p1 = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", c1, 25.5);
        Product p2 = new Product(uuid(), "book for kid", "BK5", "BOOKA",
                "BookForPeace", "This is a book", c2, 25.5);

        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        when(staffRepository.findById(staff.getId())).thenReturn(Optional.of(staff));

        DeliveryDetail d1 = new DeliveryDetail(uuid(), p1, 10);
        when(deliveryDetailRepository.findById(d1.getId())).thenReturn(Optional.of(d1));
        DeliveryDetail d2 = new DeliveryDetail(uuid(), p2, 10);
        when(deliveryDetailRepository.findById(d2.getId())).thenReturn(Optional.of(d2));

        return new DeliveryNote(uuid(), new Date(), staff, Arrays.asList(
                d1, d2
        ));
    }

    @Override
    protected List<DeliveryNote> populateListOfData() {
        Category c1 = new Category(uuid(), "bike");
        Category c2 = new Category(uuid(), "book");
        Product p1 = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", c1, 25.5);
        Product p2 = new Product(uuid(), "book for kid", "BK5", "BOOKA",
                "BookForPeace", "This is a book", c2, 25.5);
        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        return Arrays.asList(
                new DeliveryNote(uuid(), DateUtils.parse("2020-01-01"), staff, Arrays.asList(
                        new DeliveryDetail(uuid(), p1, 10),
                        new DeliveryDetail(uuid(), p2, 10)
                )),
                new DeliveryNote(uuid(), DateUtils.parse("2020-02-01"), staff, Arrays.asList(
                        new DeliveryDetail(uuid(), p1, 10),
                        new DeliveryDetail(uuid(), p2, 10)
                )),
                new DeliveryNote(uuid(), DateUtils.parse("2020-03-01"), staff, Arrays.asList(
                        new DeliveryDetail(uuid(), p1, 10),
                        new DeliveryDetail(uuid(), p2, 10)
                )),
                new DeliveryNote(uuid(), DateUtils.parse("2020-04-01"), staff, Arrays.asList(
                        new DeliveryDetail(uuid(), p1, 10),
                        new DeliveryDetail(uuid(), p2, 10)
                )),
                new DeliveryNote(uuid(), DateUtils.parse("2020-05-01"), staff, Arrays.asList(
                        new DeliveryDetail(uuid(), p1, 10),
                        new DeliveryDetail(uuid(), p2, 10)
                ))
        );
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Delivery note");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Override_CRUD_API {
        @Test
        @DisplayName("[GET] Get all without search params")
        public void getAllTest() throws Exception {
            List<DeliveryNote> data = populateListOfData();

            given(repository.findAll()).willReturn(data);
            Assertions.assertEquals(data.size(), controller.getAll(null).size());
            Assertions.assertEquals(data, controller.getAll(null));

            mockMvc.perform(
                    get("/" + endpoint + "/all").contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(data.size()))).andReturn();
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Element_Not_Found {
        @Test
        @DisplayName("[POST][ERROR] Staff not found!")
        void addTestThrowStaffNotFound() {
            try {
                DeliveryNote data = populateData();

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
        @DisplayName("[POST][ERROR] Delivery detail not found!")
        void addTestThrowDeliveryDetailNotFound() {
            try {
                DeliveryNote data = populateData();

                data.setDeliveryDetails(Collections.singletonList(new DeliveryDetail()));

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
                Assertions.assertEquals(e.getMessage(), "Delivery detail not found!");
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
            Assertions.assertEquals(controller.search(startDate, endDate, null).size(), expectedCount);
            mockMvc.perform(get("/" + endpoint).contentType(MediaType.APPLICATION_JSON)
                    .param("start", DateUtils.format(startDate))
                    .param("end", DateUtils.format(endDate)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(expectedCount)));

        }
    }
}



