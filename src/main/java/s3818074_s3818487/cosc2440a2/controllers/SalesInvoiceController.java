package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.services.SalesInvoiceService;

import java.util.UUID;

@RestController
@RequestMapping("/sales-invoices")
public class SalesInvoiceController extends AbstractController<SalesInvoice, UUID>{
    @Autowired
    public SalesInvoiceController(SalesInvoiceService service) {
        super(service);
    }
}
