package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;

import java.util.UUID;

@Service
public class OrderService extends AbstractService<Order, UUID>{
    @Autowired
    public OrderService(OrderRepository repo) {
        super(repo);
    }
}
