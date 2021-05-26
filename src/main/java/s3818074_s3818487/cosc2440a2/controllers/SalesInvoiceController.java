package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.filters.SalesInvoiceFilter;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.services.SalesInvoiceService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales-invoices")
public class SalesInvoiceController extends AbstractController<SalesInvoice, UUID> {
    private final SalesInvoiceService salesInvoiceService;

    @Autowired
    public SalesInvoiceController(SalesInvoiceService service) {
        super(service);
        this.salesInvoiceService = service;
    }

    // Without search param
    @Override
    @GetMapping("/all")
    public List<SalesInvoice> getAll(Integer page) {
        return super.getAll(page);
    }

    // With search param
    @GetMapping
    public List<SalesInvoice> search(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                     @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                     @RequestParam(value = "staff", required = false) UUID staffId,
                                     @RequestParam(value = "customer", required = false) UUID customerId,
                                     @RequestParam(value = "page", required = false) Integer page
    ) {

        return salesInvoiceService.search(startDate, endDate, staffId, customerId, page);
    }

    @GetMapping("/revenue")
    public Double getRevenue(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                             @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return salesInvoiceService.getRevenue(startDate, endDate);
    }
}
