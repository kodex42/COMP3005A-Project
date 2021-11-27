package comp.A.project.DAO;

import javax.persistence.*;

@Table(name = "book_order")
@Entity
public class BookOrderEntity {
    @EmbeddedId
    private BookOrderEntityId id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_no", insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "ISBN", insertable = false, updatable = false)
    private BookEntity book;

    public BookOrderEntity() {
        super();
    }

    public BookOrderEntity(Long o, BookEntity b, Integer q) {
        super();
        this.id = new BookOrderEntityId();
        this.id.setIsbn(b.getISBN());
        this.id.setOrderNo(o);
        this.quantity = q;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BookOrderEntityId getId() {
        return id;
    }

    public void setId(BookOrderEntityId id) {
        this.id = id;
    }

    public void setOrderNo(Long orderNo) {
        this.id.setOrderNo(orderNo);
    }
}