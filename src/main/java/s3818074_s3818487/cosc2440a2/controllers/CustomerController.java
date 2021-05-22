package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.filters.CustomerFilter;
import s3818074_s3818487.cosc2440a2.filters.SalesInvoiceFilter;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.services.CustomerService;
import s3818074_s3818487.cosc2440a2.services.SalesInvoiceService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController extends AbstractController<Customer, UUID> {

    private final SalesInvoiceService salesInvoiceService;

    @Autowired
    public CustomerController(CustomerService service, SalesInvoiceService salesInvoiceService) {
        super(service);
        this.salesInvoiceService = salesInvoiceService;
    }

    // Without search param
    @Override
    @GetMapping("/all")
    List<Customer> getAll(Integer page) {
        return super.getAll(page);
    }

    // With search param
    @GetMapping
    public List<Customer> search(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone,
                                 @RequestParam(required = false) String address,
                                 @RequestParam(required = false) Integer page) {
        return new CustomerFilter(super.getAll(page)).withName(name).withPhone(phone).withAddress(address).get();
    }

    @GetMapping("/{id}/revenue")
    public double getRevenue(@PathVariable("id") UUID id,
                             @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                             @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<SalesInvoice> salesInvoices = new SalesInvoiceFilter(salesInvoiceService.getAll()).ofCustomer(id).start(startDate).end(endDate).get();
        return salesInvoices.stream().mapToDouble(SalesInvoice::getTotalValue).sum();
    }

    @GetMapping("/{id}/sales-invoices")
    public List<SalesInvoice> getSalesInvoices(@PathVariable("id") UUID id,
                                               @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                               @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                               @RequestParam(value = "page", required = false) Integer page) {
        return new SalesInvoiceFilter(salesInvoiceService.getAll(page)).ofCustomer(id).start(startDate).end(endDate).get();
    }
}
