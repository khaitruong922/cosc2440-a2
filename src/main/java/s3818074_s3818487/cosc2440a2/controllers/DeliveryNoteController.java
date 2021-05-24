package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.filters.DeliveryNoteFilter;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.services.DeliveryNoteService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/delivery-notes")
public class DeliveryNoteController extends AbstractController<DeliveryNote, UUID> {
    private final DeliveryNoteService deliveryNoteService;

    @Autowired
    public DeliveryNoteController(DeliveryNoteService service) {
        super(service);
        this.deliveryNoteService = service;
    }

    @Override
    @GetMapping("/all")
    public List<DeliveryNote> getAll(Integer page) {
        return super.getAll(page);
    }


    @GetMapping
    public List<DeliveryNote> search(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                     @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                     @RequestParam(value = "page", required = false) Integer page) {

        return deliveryNoteService.search(startDate, endDate, page);
    }
}
