package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.filters.ReceivingNoteFilter;
import s3818074_s3818487.cosc2440a2.filters.SalesInvoiceFilter;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.services.SalesInvoiceService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales-invoices")
public class SalesInvoiceController extends AbstractController<SalesInvoice, UUID> {
    @Autowired
    public SalesInvoiceController(SalesInvoiceService service) {
        super(service);
    }

    // Without search param
    @Override
    @GetMapping("/all")
    List<SalesInvoice> getAll(Integer page) {
        return super.getAll(page);
    }

    // With search param
    @GetMapping
    List<SalesInvoice> search(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                              @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                              @RequestParam(value = "customer", required = false) UUID customerId,
                              @RequestParam(value = "staff", required = false) UUID staffId,
                              @RequestParam(value = "page", required = false) Integer page
    ) {

        return new SalesInvoiceFilter(super.getAll(page)).start(startDate).end(endDate).ofStaff(staffId).ofCustomer(customerId).get();
    }
}
