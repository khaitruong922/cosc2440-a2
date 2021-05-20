package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private double totalValue;

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

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
