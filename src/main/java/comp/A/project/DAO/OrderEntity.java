package comp.A.project.DAO;

import comp.A.project.forms.OrderForm;
import comp.A.project.services.query.AddressQueryService;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "order", schema = "public")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long orderNo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "username")
    private UserEntity user;
    private String location;
    private String status;
    private Timestamp date;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "billing_address")
    private AddressEntity billingAddress;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shipping_address")
    private AddressEntity shippingAddress;
    @Column(name = "total", precision = 10, scale = 2)
    private double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<BookOrderEntity> booksInOrder;

    public OrderEntity() {
        super();
    }

    public OrderEntity(OrderForm orderForm) {
        super();
        this.user = orderForm.getUser();
        this.location = orderForm.getLocation();
        this.status = orderForm.getStatus();
        this.date = orderForm.getDate();
        this.billingAddress = null;
        this.shippingAddress = null;
        this.total = orderForm.getTotal();
        this.booksInOrder = new ArrayList<>();
        Map<BookEntity, Integer> booksInOrder = orderForm.getBooksInOrder();
        for (BookEntity b : booksInOrder.keySet()) {
            BookOrderEntity boe = new BookOrderEntity(this.orderNo, b, booksInOrder.get(b));
            this.booksInOrder.add(boe);
        }
    }

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

    public List<BookOrderEntity> getBooksInOrder() {
        return booksInOrder;
    }

    public void setBooksInOrder(List<BookOrderEntity> booksInOrder) {
        this.booksInOrder = booksInOrder;
    }
}
