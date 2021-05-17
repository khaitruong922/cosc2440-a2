package s3818074_s3818487.cosc2440a2.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "staffs")
public class Staff extends BaseEntity {
    @Column
    private String name;

    @Column
    private String address;

    @Column(unique = true)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    // TODO: contact person

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
