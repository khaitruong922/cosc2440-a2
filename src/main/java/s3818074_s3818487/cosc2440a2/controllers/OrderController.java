package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.services.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController<Order, UUID> {
    @Autowired
    public OrderController(OrderService service) {
        super(service);
    }
}
