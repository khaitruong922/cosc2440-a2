package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "orders")
public class Order extends BaseEntity {

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Staff staff;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties("order")
    private List<OrderDetail> orderDetails;

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

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
