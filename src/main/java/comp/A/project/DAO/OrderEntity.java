package comp.A.project.DAO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order", schema = "public")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long orderNo;
    @ManyToOne
    @JoinColumn(name = "username", nullable = true)
    private UserEntity user;
    private String location;
    private String status;
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "billing_address", nullable = true)
    private AddressEntity billingAddress;
    @ManyToOne
    @JoinColumn(name = "shipping_address", nullable = true)
    private AddressEntity shippingAddress;
}
