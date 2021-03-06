package comp.A.project.services.command;

import comp.A.project.DAO.AddressEntity;
import comp.A.project.DAO.BookOrderEntity;
import comp.A.project.DAO.OrderEntity;
import comp.A.project.forms.AddressForm;
import comp.A.project.forms.OrderForm;
import comp.A.project.repositories.OrderRepository;
import comp.A.project.services.query.AddressQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookOrderEntityCommandService bookOrderEntityCommandService;
    @Autowired
    private AddressCommandService addressCommandService;
    @Autowired
    private AddressQueryService addressQueryService;
    @Autowired
    private UserCommandService userCommandService;


    public OrderEntity createOrder(OrderForm orderForm) {
        OrderEntity order = new OrderEntity(orderForm);

        // Create addresses if needed independently of the order
        order.setBillingAddress(addressCommandService.create(orderForm.getBillingAddress()));
        order.setShippingAddress(addressCommandService.create(orderForm.getShippingAddress()));
        // Save addresses to user if save was requested
        if (orderForm.getSaveBilling())
            orderForm.getUser().setBillingAddress(order.getBillingAddress());
        if (orderForm.getSaveShipping())
            orderForm.getUser().setShippingAddress(order.getShippingAddress());
        userCommandService.saveUser(orderForm.getUser());
        // Save the order
        OrderEntity saved = orderRepository.save(order);
        // Use the orderNo from the saved entity generated by the repository to save the book_order entries
        for (int i = 0; i < order.getBooksInOrder().size(); i++) {
            BookOrderEntity boe = order.getBooksInOrder().get(i);
            boe.setOrderNo(saved.getOrderNo());
            order.getBooksInOrder().set(i, bookOrderEntityCommandService.create(boe));
        }
        return saved;
    }
}
