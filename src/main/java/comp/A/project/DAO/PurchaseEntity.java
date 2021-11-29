package comp.A.project.DAO;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "purchase")
@Entity
public class PurchaseEntity {
    @EmbeddedId
    private PurchaseEntityCandidateKey id;
    @Column(name = "total_cost", precision = 10, scale = 2)
    private Double totalCost;

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public PurchaseEntityCandidateKey getId() {
        return id;
    }

    public void setId(PurchaseEntityCandidateKey id) {
        this.id = id;
    }


}