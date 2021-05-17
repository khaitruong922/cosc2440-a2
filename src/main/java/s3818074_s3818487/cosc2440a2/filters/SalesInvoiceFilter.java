package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SalesInvoiceFilter {
    private List<SalesInvoice> salesInvoices;

    public SalesInvoiceFilter(List<SalesInvoice> salesInvoices) {
        this.salesInvoices = salesInvoices;
    }

    public SalesInvoiceFilter start(Date startDate) {
        if (startDate == null) return this;
        salesInvoices = salesInvoices.stream().filter(i -> DateUtils.isAfterInclusive(i.getDate(), startDate)).collect(Collectors.toList());
        return this;
    }

    public SalesInvoiceFilter in(Date date) {
        if (date == null) return this;
        salesInvoices = salesInvoices.stream().filter(i -> DateUtils.isSameDay(i.getDate(), date)).collect(Collectors.toList());
        return this;
    }

    public SalesInvoiceFilter end(Date endDate) {
        if (endDate == null) return this;
        salesInvoices = salesInvoices.stream().filter(i -> DateUtils.isBeforeInclusive(i.getDate(), endDate)).collect(Collectors.toList());
        return this;
    }

    public SalesInvoiceFilter ofStaff(UUID id) {
        if (id == null) return this;
        salesInvoices = salesInvoices.stream().filter(i -> i.getStaff().getId().equals(id)).collect(Collectors.toList());
        return this;
    }

    public SalesInvoiceFilter ofCustomer(UUID id) {
        if (id == null) return this;
        salesInvoices = salesInvoices.stream().filter(i -> i.getCustomer().getId().equals(id)).collect(Collectors.toList());
        return this;
    }

    public List<SalesInvoice> get() {
        return salesInvoices;
    }

}
