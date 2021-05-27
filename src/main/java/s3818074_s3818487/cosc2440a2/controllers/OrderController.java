package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.services.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController<Order, UUID> {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService service) {
        super(service);
        this.orderService = service;
    }

    @PostMapping("/{id}/receiving-note")
    public ReceivingNote createReceivingNote(@PathVariable("id") UUID id) {
        return orderService.createReceivingNote(id);
    }
}
