package comp.A.project.DAO;

import javax.persistence.*;

@Entity
@Table(name = "publisher", schema = "public")
public class PublisherEntity {
    @Id
    private String name;
    @ManyToOne
    @JoinColumn(name = "address", nullable = true)
    private AddressEntity address;
    private String email;
    private String phone;
    private String bankAccountNo;
}
