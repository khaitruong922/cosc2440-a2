package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.OrderDetail;
import s3818074_s3818487.cosc2440a2.services.OrderDetailService;

import java.util.UUID;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController extends AbstractController<OrderDetail, UUID> {
    @Autowired
    public OrderDetailController(OrderDetailService service) {
        super(service);
    }
}
