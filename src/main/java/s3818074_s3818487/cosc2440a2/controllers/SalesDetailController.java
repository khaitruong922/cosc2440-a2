package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.SalesDetail;
import s3818074_s3818487.cosc2440a2.services.SalesDetailService;

import java.util.UUID;

@RestController
@RequestMapping("/sales-details")
public class SalesDetailController extends AbstractController<SalesDetail, UUID> {
    protected SalesDetailController(SalesDetailService service) {
        super(service);
    }
}
