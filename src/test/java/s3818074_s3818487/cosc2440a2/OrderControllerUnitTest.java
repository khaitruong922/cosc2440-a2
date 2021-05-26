package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import s3818074_s3818487.cosc2440a2.controllers.OrderController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.repositories.OrderDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.repositories.ProviderRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;
import s3818074_s3818487.cosc2440a2.services.OrderService;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderControllerUnitTest extends AbstractUnitTest<Order> {
    @InjectMocks
    @Autowired
    private OrderController controller;

    public OrderControllerUnitTest() {
        super("orders");
    }

    @MockBean
    protected OrderRepository repository;

    @MockBean
    protected StaffRepository staffRepository;

    @MockBean
    protected ProviderRepository providerRepository;

    @MockBean
    protected OrderDetailRepository orderDetailRepository;

    @InjectMocks
    @Autowired
    protected OrderService service;

    @BeforeEach
    public void init() {
        setup(controller, service, repository);
    }

    @Override
    protected Order populateData() {
        Category category = new Category(uuid(), "bike");
        Product product = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", category, 25.5);

        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        when(staffRepository.findById(staff.getId())).thenReturn(Optional.of(staff));

        Provider provider = new Provider(uuid(), "Tin Provider", "123 ABC",
                "0909090888", "123", "admin@email.com", "Chung Quan Tin");
        when(providerRepository.findById(provider.getId())).thenReturn(Optional.of(provider));

        OrderDetail orderDetail = new OrderDetail(uuid(), null, 10,
                20.0);
        when(orderDetailRepository.findById(orderDetail.getId())).thenReturn(Optional.of(orderDetail));

        List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
        return new Order(uuid(), new Date(), staff, provider, orderDetails);
    }

    @Override
    protected List<Order> populateListOfData() {
        // Setup our mock repository
        Category category = new Category(uuid(), "bike");
        Product product = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", category, 25.5);
        Staff staff = new Staff(uuid(), "Tin Staff", "123 ABC", "0909090888",
                "admin@email.com", "Chung Quan Tin");
        Provider provider = new Provider(uuid(), "Tin Provider", "123 ABC",
                "0909090888", "123", "admin@email.com", "Chung Quan Tin");
        OrderDetail orderDetail = new OrderDetail(uuid(), product, 10,
                product.getSellingPrice() * 10);
        List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
        return Arrays.asList(
                new Order(uuid(), new Date(), staff, provider, orderDetails),
                new Order(uuid(), new Date(), staff, provider, Collections.emptyList()));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Element_Not_Found {

        @Test
        @DisplayName("[POST][ERROR] Staff not found!")
        void addTestThrowStaffNotFound() {
            try {
                Order data = populateData();

                data.setStaff(new Staff());

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/orders")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e) {
                Assertions.assertEquals(e.getMessage(), "Staff not found!");
            }
        }

        @Test
        @DisplayName("[POST][ERROR] Provider not found!")
        void addTestThrowProviderNotFound() {
            try {
                Order data = populateData();

                data.setProvider(new Provider());

                when(repository.save(data)).thenReturn(data);
                Assertions.assertEquals(data, service.add(data));

                // Assertions
                String jsonRequest = om.writeValueAsString(data);
                mockMvc.perform(
                        post("/orders")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();
            } catch (Exception e) {
                Assertions.assertEquals(e.getMessage(), "Provider not found");
            }
        }
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Order");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Additional_API {
        @Test
        @DisplayName("[POST] Create receiving note from order")
        public void createReceivingNoteTest() throws Exception {
            Order order = new Order(uuid(), DateUtils.parse("2020-01-01"), new Staff(), new Provider(), Arrays.asList(
                    new OrderDetail(uuid(), new Product(), 10, 20.0),
                    new OrderDetail(uuid(), new Product(), 10, 20.0)
            ));
            when(repository.findById(order.getId())).thenReturn(Optional.of(order));
            ReceivingNote receivingNote = controller.createReceivingNote(order.getId());

            Assertions.assertEquals(order.getStaff(), receivingNote.getStaff());
            Assertions.assertEquals(order.getDate(), receivingNote.getDate());
            Assertions.assertEquals(order.getOrderDetails().size(), receivingNote.getReceivingDetails().size());


            mockMvc.perform(post("/" + endpoint + "/" + order.getId() + "/receiving-note").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }
    }

}
