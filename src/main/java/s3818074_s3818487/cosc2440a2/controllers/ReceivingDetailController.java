package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.ReceivingDetail;
import s3818074_s3818487.cosc2440a2.services.ReceivingDetailService;

import java.util.UUID;

@RestController
@RequestMapping("/receiving-details")
public class ReceivingDetailController extends AbstractController<ReceivingDetail, UUID> {
    @Autowired
    public ReceivingDetailController(ReceivingDetailService service) {
        super(service);
    }
}
