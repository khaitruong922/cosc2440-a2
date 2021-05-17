package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SalesInvoiceFilter extends DateFilter<SalesInvoice> {

    public SalesInvoiceFilter(List<SalesInvoice> dateEntities) {
        super(dateEntities);
    }
}
