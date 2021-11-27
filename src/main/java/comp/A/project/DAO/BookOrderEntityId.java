package comp.A.project.DAO;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookOrderEntityId implements Serializable {
    private static final long serialVersionUID = -189700714906099224L;
    @Column(name = "order_no", nullable = false)
    private Long orderNo;
    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, isbn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookOrderEntityId entity = (BookOrderEntityId) o;
        return Objects.equals(this.orderNo, entity.orderNo) &&
                Objects.equals(this.isbn, entity.isbn);
    }
}