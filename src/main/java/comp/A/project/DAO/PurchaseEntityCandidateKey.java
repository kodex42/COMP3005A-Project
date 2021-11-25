package comp.A.project.DAO;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Embeddable
public class PurchaseEntityCandidateKey implements Serializable {
    private static final long serialVersionUID = -6485201090205020981L;
    @Column(name = "isbn", nullable = false, length = 13)
    private String ISBN;
    @Column(name = "date", nullable = false)
    private Timestamp date;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, quantity, ISBN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PurchaseEntityCandidateKey entity = (PurchaseEntityCandidateKey) o;
        return Objects.equals(this.date, entity.date) &&
                Objects.equals(this.quantity, entity.quantity) &&
                Objects.equals(this.ISBN, entity.ISBN);
    }
}