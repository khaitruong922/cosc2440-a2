package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.filters.ReceivingNoteFilter;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.services.ReceivingNoteService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receiving-notes")
public class ReceivingNoteController extends AbstractController<ReceivingNote, UUID> {
    @Autowired
    public ReceivingNoteController(ReceivingNoteService service) {
        super(service);
    }

    // Without search param
    @Override
    @GetMapping("/all")
    public List<ReceivingNote> getAll(Integer page) {
        return super.getAll(page);
    }

    // With search param
    @GetMapping
    public List<ReceivingNote> search(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                               @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                               @RequestParam(value = "page", required = false) Integer page) {

        return new ReceivingNoteFilter(super.getAll(page)).start(startDate).end(endDate).get();
    }
}
