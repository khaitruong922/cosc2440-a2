package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

// TODO:  To make it easier for warehouse keepers, data of a note should be transferred from an order.
@Entity
@Table(name = "receivingDetails")
public class ReceivingDetail extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnoreProperties(value = "receivingDetails", allowSetters = true)
    private ReceivingNote receivingNote;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    public ReceivingDetail() {
    }

    public ReceivingDetail(UUID id, Product product, int quantity) {
        super(id);
        this.product = product;
        this.quantity = quantity;
    }

    @Column
    private Integer quantity;

    public ReceivingNote getReceivingNote() {
        return receivingNote;
    }

    public void setReceivingNote(ReceivingNote receivingNote) {
        this.receivingNote = receivingNote;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
