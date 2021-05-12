package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class SalesInvoiceService extends AbstractService<SalesInvoice, UUID> {
    @Autowired
    public SalesInvoiceService(SalesInvoiceRepository repo) {
        super(repo);
    }
}
