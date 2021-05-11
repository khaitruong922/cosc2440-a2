package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.services.ReceivingNoteService;

import java.util.UUID;

@RestController
@RequestMapping("/receiving-notes")
public class ReceivingNoteController extends AbstractController<ReceivingNote, UUID>{
    @Autowired
    public ReceivingNoteController(ReceivingNoteService service) {
        super(service);
    }
}
