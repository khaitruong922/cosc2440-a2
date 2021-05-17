package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SalesInvoiceFilter {
    private List<SalesInvoice> salesInvoices;

    public SalesInvoiceFilter(List<SalesInvoice> salesInvoices) {
        this.salesInvoices = salesInvoices;
    }

    public SalesInvoiceFilter start(Date startDate) {
        if (startDate == null) return this;
        salesInvoices = salesInvoices.stream().filter(invoice -> invoice.getDate().after(startDate)).collect(Collectors.toList());
        return this;
    }

    public SalesInvoiceFilter end(Date endDate) {
        if (endDate == null) return this;
        salesInvoices = salesInvoices.stream().filter(invoice -> invoice.getDate().before(endDate)).collect(Collectors.toList());
        return this;
    }

    public List<SalesInvoice> get() {
        return salesInvoices;
    }
}
