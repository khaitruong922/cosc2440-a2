package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import s3818074_s3818487.cosc2440a2.controllers.ReceivingNoteController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;
import s3818074_s3818487.cosc2440a2.services.ReceivingNoteService;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ReceivingNoteUnitTest extends AbstractUnitTest<ReceivingNote> {
    @InjectMocks
    @Autowired
    private ReceivingNoteController controller;

    public ReceivingNoteUnitTest() {
        super("receiving-notes");
    }

    @MockBean
    protected ReceivingNoteRepository repository;

    @MockBean
    protected ReceivingDetailRepository receivingDetailRepository;

    @MockBean
    protected StaffRepository staffRepository;

    @Autowired
    protected ReceivingNoteService service;

    @BeforeEach
    public void init() {
        setUp(controller, service, repository);
    }

    @Override
    protected ReceivingNote populateData() {
        Category c1 = new Category(uuid(), "bike");
        Category c2 = new Category(uuid(), "book");
        Product p1 = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", c1, 25.5);
        Product p2 = new Product(uuid(), "book for kid", "BK5", "BOOKA",
                "BookForPeace", "This is a book", c2, 25.5);

        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        when(staffRepository.findById(staff.getId())).thenReturn(Optional.of(staff));

        ReceivingDetail rd1 = new ReceivingDetail(uuid(), p1, 10);
        when(receivingDetailRepository.findById(rd1.getId())).thenReturn(Optional.of(rd1));
        ReceivingDetail rd2 = new ReceivingDetail(uuid(), p2, 10);
        when(receivingDetailRepository.findById(rd2.getId())).thenReturn(Optional.of(rd2));

        return new ReceivingNote(uuid(), new Date(), staff, Arrays.asList(
                rd1, rd2
        ));
    }

    @Override
    protected List<ReceivingNote> populateListOfData() {
        Category c1 = new Category(uuid(), "bike");
        Category c2 = new Category(uuid(), "book");
        Product p1 = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", c1, 25.5);
        Product p2 = new Product(uuid(), "book for kid", "BK5", "BOOKA",
                "BookForPeace", "This is a book", c2, 25.5);
        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        return Arrays.asList(
                new ReceivingNote(uuid(), new Date(), staff, Arrays.asList(
                        new ReceivingDetail(uuid(), p1, 10),
                        new ReceivingDetail(uuid(), p2, 10)
                )),
                new ReceivingNote(uuid(), new Date(), staff, Arrays.asList(
                        new ReceivingDetail(uuid(), p1, 10),
                        new ReceivingDetail(uuid(), p2, 10)
                )),
                new ReceivingNote(uuid(), new Date(), staff, Arrays.asList(
                        new ReceivingDetail(uuid(), p1, 10),
                        new ReceivingDetail(uuid(), p2, 10)
                )),
                new ReceivingNote(uuid(), new Date(), staff, Arrays.asList(
                        new ReceivingDetail(uuid(), p1, 10),
                        new ReceivingDetail(uuid(), p2, 10)
                )),
                new ReceivingNote(uuid(), new Date(), staff, Arrays.asList(
                        new ReceivingDetail(uuid(), p1, 10),
                        new ReceivingDetail(uuid(), p2, 10)
                ))
        );
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Receiving note");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Element_Not_Found{

        @Test
        @DisplayName("[POST][ERROR] Staff not found!")
        void addTestThrowStaffNotFound(){
            try {
                ReceivingNote data = populateData();

                data.setStaff(new Staff());

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/receiving-notes")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e){
                Assertions.assertEquals(e.getMessage(), "Staff not found!");
            }
        }
        @Test
        @DisplayName("[POST][ERROR] Receiving detail not found!")
        void addTestThrowReceivingDetailNotFound(){
            try {
                ReceivingNote data = populateData();

                data.setReceivingDetails(Collections.singletonList(new ReceivingDetail()));

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/receiving-notes")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e){
                Assertions.assertEquals(e.getMessage(), "Receiving detail not found!");
            }
        }
    }
}



