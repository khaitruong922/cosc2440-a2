package s3818074_s3818487.cosc2440a2;

import org.junit.Assert;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.OrderController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.models.Order;

import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderControllerUnitTest extends AbstractUnitTest<Order>{
	@InjectMocks
	private OrderController controller;

	@BeforeEach
	public void init(){
		setUp(controller);
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	@DisplayName("[POST] Create Order")
	public void addOrderTest() throws Exception {
		// Setup our mock repository
		Category category = new Category(genUUID(),"bike");
		Product product = new Product(genUUID(),"bike for kid", "BK3","BKA",
				"BikeForPeace","This is a bike",category,25.5);
		Staff staff = new Staff(genUUID(),"Tin Staff", "123 ABC", "0909090888",
				"admin@email.com","Chung Quan Tin");
		Provider provider = new Provider(genUUID(),"Tin Provider", "123 ABC",
				"0909090888", "123","admin@email.com","Chung Quan Tin");
		OrderDetail orderDetail = new OrderDetail(genUUID(),product,10,
				(float) (product.getSellingPrice() * 10));
		List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
		UUID orderId = genUUID();
		Order order = new Order(orderId,new Date(),staff,provider, orderDetails);

		when(service.add(order)).thenReturn(order);
		Assert.assertEquals(service.add(order), order);

		when(service.getAll()).thenReturn(Collections.singletonList(order));
		Assert.assertEquals(service.getAll().size(), 1);

		// Assertions
		String jsonRequest = om.writeValueAsString(order);
		mockMvc.perform(
				post("/orders")
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	@DisplayName("[GET] Get Orders")
	public void getOrdersTest() throws Exception {
		// Setup our mock repository
		Category category = new Category(genUUID(),"bike");
		Product product = new Product(genUUID(),"bike for kid", "BK3","BKA",
				"BikeForPeace","This is a bike",category,25.5);
		Staff staff = new Staff(genUUID(),"Tin Staff", "123 ABC", "0909090888",
				"admin@email.com","Chung Quan Tin");
		Provider provider = new Provider(genUUID(),"Tin Provider", "123 ABC",
				"0909090888", "123","admin@email.com","Chung Quan Tin");
		OrderDetail orderDetail = new OrderDetail(genUUID(),product,10,
				(float) (product.getSellingPrice() * 10));
		List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
		List<Order> orders = Arrays.asList(
				new Order(genUUID(),new Date(),staff,provider, orderDetails),
				new Order(genUUID(),new Date(),staff,provider, Collections.emptyList()));

		when(service.getAll()).thenReturn(orders);
		Assert.assertEquals(service.getAll().size(), 2);
		Assert.assertEquals(service.getAll(), orders);

		mockMvc.perform(
				get("/orders").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	@DisplayName("[GET] Get Order")
	public void getOrderTest() throws Exception {}

	@Test
	@org.junit.jupiter.api.Order(5)
	@DisplayName("[DELETE] Delete Orders")
	public void deleteOrdersTest() throws Exception {}

	@Test
	@org.junit.jupiter.api.Order(5)
	@DisplayName("[DELETE] Delete Order")
	public void deleteOrderTest() throws Exception {}

	@Test
	@org.junit.jupiter.api.Order(5)
	@DisplayName("[PUT] Update Order")
	public void updateOrderTest() throws Exception {}
}
