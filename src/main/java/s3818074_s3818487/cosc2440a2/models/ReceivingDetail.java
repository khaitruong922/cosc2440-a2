package s3818074_s3818487.cosc2440a2.models;

import javax.persistence.*;
import java.util.UUID;

// TODO:  To make it easier for warehouse keepers, data of a note should be transferred from an order.
@Entity(name = "receivingDetail")
public class ReceivingDetail {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "receivingNoteId", referencedColumnName = "id")
    private ReceivingNote receivingNote;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    @Column
    private int quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}