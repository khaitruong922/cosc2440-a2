package s3818074_s3818487.cosc2440a2.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.services.OrderService;

import javax.persistence.criteria.Order;
import java.util.Optional;

@SpringBootTest
public class TestOrderController {

    @Autowired
    private OrderService service;

    @MockBean
    private OrderRepository repository;

    @Test
    @DisplayName("[Order | GET] Success")
    void testGetOrder() {
        // Setup our mock repository
//        Order widget = new Order(1l, "Widget Name", "Description", 1);
//        Mockito.doReturn(Optional.of(widget)).when(repository).findById(1l);
//
//        // Execute the service call
//        Optional<Widget> returnedWidget = service.findById(1l);
//
//        // Assert the response
//        Assertions.assertTrue(returnedWidget.isPresent(), "Widget was not found");
//        Assertions.assertSame(returnedWidget.get(), widget, "The widget returned was not the same as the mock");
    }

}
