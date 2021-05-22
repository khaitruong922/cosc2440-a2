package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.filters.SalesInvoiceFilter;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.services.SalesInvoiceService;
import s3818074_s3818487.cosc2440a2.services.StaffService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/staffs")
public class StaffController extends AbstractController<Staff, UUID> {

    private final SalesInvoiceService salesInvoiceService;

    @Autowired
    public StaffController(StaffService service, SalesInvoiceService salesInvoiceService) {
        super(service);
        this.salesInvoiceService = salesInvoiceService;
    }

    @GetMapping("/{id}/revenue")
    public double getRevenue(@PathVariable("id") UUID id,
                             @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                             @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<SalesInvoice> salesInvoices = new SalesInvoiceFilter(salesInvoiceService.getAll()).ofStaff(id).start(startDate).end(endDate).get();
        return salesInvoices.stream().mapToDouble(SalesInvoice::getTotalValue).sum();
    }

    @GetMapping("/{id}/sales-invoices")
    public List<SalesInvoice> getSalesInvoices(@PathVariable("id") UUID id,
                                               @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                               @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                               @RequestParam(value = "page", required = false) Integer page) {
        return new SalesInvoiceFilter(salesInvoiceService.getAll(page)).ofStaff(id).start(startDate).end(endDate).get();
    }
}
