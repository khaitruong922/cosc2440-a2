package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.OrderController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.services.OrderService;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderControllerUnitTest extends AbstractUnitTest<Order> {
    @InjectMocks
    private OrderController controller;

    public OrderControllerUnitTest() {
        super("orders");
    }

    @MockBean
    protected OrderRepository repository;

    @Autowired
    protected OrderService service;

    @BeforeEach
    public void init() {
        setUp(controller, service, repository);
    }

    @Override
    protected Order populateData() {
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
}
