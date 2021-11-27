package comp.A.project.services.command;

import comp.A.project.DAO.OrderEntity;
import comp.A.project.forms.OrderForm;
import comp.A.project.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity createOrder(OrderForm orderForm) {
        return orderRepository.save(new OrderEntity(orderForm));
    }
}
