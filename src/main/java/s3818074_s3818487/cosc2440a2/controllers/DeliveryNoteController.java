package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.services.DeliveryNoteService;

import java.util.UUID;

@RestController
@RequestMapping("/delivery-notes")
public class DeliveryNoteController extends AbstractController<DeliveryNote, UUID>{
    @Autowired
    public DeliveryNoteController(DeliveryNoteService service) {
        super(service);
    }
}
