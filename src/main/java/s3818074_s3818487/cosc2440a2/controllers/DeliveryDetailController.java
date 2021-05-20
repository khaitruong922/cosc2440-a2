package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.DeliveryDetail;
import s3818074_s3818487.cosc2440a2.services.DeliveryDetailService;

import java.util.UUID;

@RestController
@RequestMapping("/delivery-details")
public class DeliveryDetailController extends AbstractController<DeliveryDetail, UUID> {
    public DeliveryDetailController(DeliveryDetailService service) {
        super(service);
    }
}
