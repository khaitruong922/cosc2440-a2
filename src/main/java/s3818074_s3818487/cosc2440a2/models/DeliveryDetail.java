package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

// TODO:  To make it easier for warehouse keepers, data of a note should be transferred from an order.
@Entity
@Table(name = "deliveryDetails")
public class DeliveryDetail extends BaseEntity {
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnoreProperties(value = "deliveryDetails", allowSetters = true)
    private DeliveryNote deliveryNote;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    @Column
    private int quantity;

    public DeliveryDetail() {
    }

    public DeliveryDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
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
}
