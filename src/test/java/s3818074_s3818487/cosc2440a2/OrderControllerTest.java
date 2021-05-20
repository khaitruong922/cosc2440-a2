package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import s3818074_s3818487.cosc2440a2.controllers.OrderController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.services.OrderService;
import org.junit.Test;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    protected OrderService service;

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected OrderRepository repository;

    @Test
    @DisplayName("[Order | GET] Success")
    public void getOrders() throws Exception {
        // Setup our mock repository
        Category category = new Category("bike");
        Product product = new Product("bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",category,25.5);
        Staff staff = new Staff("Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        Provider provider = new Provider("Tin Provider", "123 ABC",
                "0909090888", "123","admin@email.com","Chung Quan Tin");
        OrderDetail orderDetail = new OrderDetail(product,10, (float) (product.getSellingPrice() * 10));
        List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
        Order order = new Order(new Date(),staff,provider, orderDetails);

        service.add(order);

        List<Order> allOrders = Collections.singletonList(order);

        // Assertions
        given(service.getAll()).willReturn(allOrders);

        mvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
