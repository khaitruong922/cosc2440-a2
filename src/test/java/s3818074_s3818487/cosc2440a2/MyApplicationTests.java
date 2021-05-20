package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import s3818074_s3818487.cosc2440a2.models.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MyApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void addOrderTest() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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

		// Assertions
		String jsonRequest = om.writeValueAsString(order);
		System.out.println(jsonRequest);
		MvcResult result = mockMvc.perform(
				post("/orders")
				.content(jsonRequest)
				.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Order resOrder = om.readValue(resultContent, Order.class);

		Assert.assertEquals(resOrder, order);
	}

	@Test
	public void getOrdersTest() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		System.out.println(mockMvc);
		MvcResult result = mockMvc.perform(
				get("/orders").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		System.out.println();
		String resultContent = result.getResponse().getContentAsString();
		System.out.println(resultContent);
	}
}
