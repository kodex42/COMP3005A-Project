package comp.A.project.services.command;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.PurchaseEntity;
import comp.A.project.DAO.PurchaseEntityCandidateKey;
import comp.A.project.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class PurchaseCommandService {
    private static final double PUBLISHER_DISCOUNT = 0.5;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseEntity createPurchase(BookEntity bookEntity, Integer quantity) {
        PurchaseEntityCandidateKey candidateKey = new PurchaseEntityCandidateKey();
        candidateKey.setISBN(bookEntity.getISBN());
        candidateKey.setQuantity(quantity);
        candidateKey.setDate(Timestamp.from(Instant.now()));
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(candidateKey);
        purchaseEntity.setTotalCost(bookEntity.getPrice() * quantity * PUBLISHER_DISCOUNT);
        return purchaseRepository.save(purchaseEntity);
    }

    public void remove(PurchaseEntity purchaseEntity) {
        purchaseRepository.delete(purchaseEntity);
    }
}
