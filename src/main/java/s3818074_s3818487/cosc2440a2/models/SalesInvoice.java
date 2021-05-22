package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "salesInvoices")
public class SalesInvoice extends BaseEntity {

    @Column
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "salesInvoice")
    @JsonIgnoreProperties(value = "salesInvoice", allowSetters = true)
    private List<SalesDetail> salesDetails;

    @Column
    private Double totalValue;

    public SalesInvoice(UUID id, Date date, Staff staff, Customer customer, List<SalesDetail> salesDetails, Double totalValue) {
        super(id);
        this.date = date;
        this.staff = staff;
        this.customer = customer;
        this.salesDetails = salesDetails;
        this.totalValue = totalValue;
    }

    public SalesInvoice() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SalesDetail> getSalesDetails() {
        return salesDetails;
    }

    public void setSalesDetails(List<SalesDetail> salesDetails) {
        this.salesDetails = salesDetails;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
