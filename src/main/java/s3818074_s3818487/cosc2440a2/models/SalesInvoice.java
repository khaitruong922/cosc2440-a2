package s3818074_s3818487.cosc2440a2.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "salesInvoices")
public class SalesInvoice extends  BaseEntity{

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SalesDetail> SaleDetail;

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

    public List<SalesDetail> getSaleDetail() {
        return SaleDetail;
    }

    public void setSaleDetail(List<SalesDetail> saleDetail) {
        SaleDetail = saleDetail;
    }
}
