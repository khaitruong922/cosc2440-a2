package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "orderDetails")
public class OrderDetail extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "orderDetails", allowSetters = true)
    @JoinColumn(referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    @Column
    private int quantity;

    @Column
    private float price;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderDetail(UUID id, Product product, int quantity, float price) {
        super(id);
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail() {
    }
}
