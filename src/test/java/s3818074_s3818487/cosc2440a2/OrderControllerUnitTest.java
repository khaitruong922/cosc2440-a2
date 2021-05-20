package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import s3818074_s3818487.cosc2440a2.controllers.OrderController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.services.OrderService;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderControllerUnitTest {

	private MockMvc mockMvc;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	ObjectMapper om = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		om.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void contextLoads() {
		Assert.assertNotNull(mockMvc);
	}

	private UUID genUUID(){
		return UUID.randomUUID();
	}

	@Test
	@org.junit.jupiter.api.Order(2)
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

		when(orderService.getById(orderId)).thenReturn(order);

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

		when(orderService.getAll()).thenReturn(orders);

		mockMvc.perform(
				get("/orders").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
	}
}
