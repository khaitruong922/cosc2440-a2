package s3818074_s3818487.cosc2440a2.models;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "receivingNotes")
public class ReceivingNote extends BaseEntity {

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Staff staff;

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
}
