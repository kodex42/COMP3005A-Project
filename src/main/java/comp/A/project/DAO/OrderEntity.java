package comp.A.project.DAO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "order", schema = "public")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long orderNo;
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
    @Column(name = "total", precision = 10, scale = 2)
    private double total;

    @ManyToMany
    @JoinTable(
            name = "book_order",
            joinColumns = @JoinColumn(name = "order_no"),
            inverseJoinColumns = @JoinColumn(name = "ISBN")
    )
    private List<BookEntity> booksInOrder;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public AddressEntity getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressEntity billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<BookEntity> getBooksInOrder() {
        return booksInOrder;
    }

    public void setBooksInOrder(List<BookEntity> booksInOrder) {
        this.booksInOrder = booksInOrder;
    }
}
