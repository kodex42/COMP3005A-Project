package comp.A.project.services.User;

import comp.A.project.DAO.PurchaseEntity;
import comp.A.project.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseQueryService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public Iterable<PurchaseEntity> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public double getTotalPurchases() {
        return purchaseRepository.sumTotals();
    }
}
