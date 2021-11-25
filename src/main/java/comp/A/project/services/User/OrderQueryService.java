package comp.A.project.services.User;

import comp.A.project.DAO.OrderEntity;
import comp.A.project.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderQueryService {
    @Autowired
    private OrderRepository orderRepository;

    public Iterable<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public double getTotalIncome() {
        return orderRepository.sumTotals();
    }
}
