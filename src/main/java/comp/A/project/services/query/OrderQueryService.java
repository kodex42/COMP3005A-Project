package comp.A.project.services.query;

import comp.A.project.DAO.OrderEntity;
import comp.A.project.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class OrderQueryService {
    @Autowired
    private OrderRepository orderRepository;

    public Iterable<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Iterable<Object[]> getOrderTotalsGroupedByAuthorName() {
        return orderRepository.orderTotalsGroupedByAuthorName();
    }

    public Iterable<Object[]> getOrderTotalsGroupedByGenre() {
        return orderRepository.orderTotalsGroupedByGenre();
    }

    public Iterable<Object[]> getOrderTotalsGroupedByPublisher() {
        return orderRepository.orderTotalsGroupedByPublisher();
    }

    public Iterable<Object[]> getOrderTotalsWithinDateRange(Timestamp t1, Timestamp t2) {
        return orderRepository.orderTotalsWithinDateRange(t1, t2);
    }
}
