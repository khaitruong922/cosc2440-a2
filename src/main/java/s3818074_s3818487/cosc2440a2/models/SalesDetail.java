package s3818074_s3818487.cosc2440a2.models;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "salesDetail")
public class SalesDetail {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;

    @Column
    private int quantity;

    // TODO this price will be copied from product price, after copying users can change them manually by another API if needed
    @Column
    private float price;

    // TODO product price * quantity
    @Column
    private float totalValue;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }
}
