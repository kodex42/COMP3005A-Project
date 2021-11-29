package comp.A.project.repositories;

import comp.A.project.DAO.PurchaseEntity;
import comp.A.project.DAO.PurchaseEntityCandidateKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<PurchaseEntity, PurchaseEntityCandidateKey> {

    @Query(value = "SELECT COALESCE(SUM(total_cost), 0) FROM purchase", nativeQuery = true)
    double sumTotals();

    @Query(value = "SELECT * FROM purchase WHERE ISBN = ?1", nativeQuery = true)
    Iterable<PurchaseEntity> getAllWithIsbn(String isbn);
}
