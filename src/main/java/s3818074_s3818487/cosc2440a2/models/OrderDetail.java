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
    private Integer quantity;

    @Column
    private Double price;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderDetail(UUID id, Product product, Integer quantity, Double price) {
        super(id);
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail() {
    }
}
