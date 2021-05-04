package s3818074.cosc2440a2.models;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;
}
