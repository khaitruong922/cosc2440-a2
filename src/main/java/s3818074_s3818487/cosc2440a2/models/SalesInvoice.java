package s3818074_s3818487.cosc2440a2.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name="salesInvoice")
public class SalesInvoice {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private String nameOfSalesStaff;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SalesDetail> SaleDetail;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameOfSalesStaff() {
        return nameOfSalesStaff;
    }

    public void setNameOfSalesStaff(String nameOfSalesStaff) {
        this.nameOfSalesStaff = nameOfSalesStaff;
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
