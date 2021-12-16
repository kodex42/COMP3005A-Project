package comp.A.project.controllers;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.OrderEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.AddressForm;
import comp.A.project.forms.OrderForm;
import comp.A.project.services.command.BookCommandService;
import comp.A.project.services.command.OrderCommandService;
import comp.A.project.services.command.PurchaseCommandService;
import comp.A.project.services.query.AddressQueryService;
import comp.A.project.services.query.OrderQueryService;
import comp.A.project.services.query.UserQueryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

/*  OrderController

    Handles the following routes:
        GET
            /order
        POST
            /order/create

*/
@Controller
@SessionAttributes("cart")
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private HomeController homeController;
    @Autowired
    private UserController userController;
    @Autowired
    private BookCommandService bookCommandService;
    @Autowired
    private AddressQueryService addressQueryService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private OrderCommandService orderCommandService;
    @Autowired
    private PurchaseCommandService purchaseCommandService;

    @ModelAttribute("cart")
    public Map<BookEntity, Integer> initCart(Principal principal) throws NotFoundException {
        return homeController.getShoppingCart(userController.getCurrentUser(principal));
    }

    @GetMapping("")
    public String getOrder(Model model, Principal principal) throws NotFoundException {
        log.info("Request: view order form");

        UserEntity user = userController.getCurrentUser(principal);
        Map<BookEntity, Integer> cart = homeController.getShoppingCart(user);
        double total = 0;
        for (BookEntity b : cart.keySet())
            total += b.getPrice() * cart.get(b);

        OrderForm form = new OrderForm();
        form.setUser(user);
        form.setTotal(total);
        form.setBooksInOrder(cart);
        form.setBillingAddress(new AddressForm(user.getBillingAddress()));
        form.setShippingAddress(new AddressForm(user.getShippingAddress()));
        model.addAttribute("orderForm", form);
        model.addAttribute("cart", cart);
        return "order";
    }

    @PostMapping("/create")
    public String createOrder(@Valid @ModelAttribute("cart") final Map<BookEntity, Integer> cart, @Valid OrderForm orderForm, BindingResult bindingResult, Model model, Principal principal) {
        log.info("Request: create order for " + orderForm.getUser().getUsername());

        orderForm.setBooksInOrder(cart);
        if (!bindingResult.hasErrors()) {
            try {
                OrderEntity order = orderCommandService.createOrder(orderForm);
                UserEntity user = userController.getCurrentUser(principal);
                // Retrieve and reset shopping cart
//                Map<BookEntity, Integer> cart = Map.copyOf(orderForm.getBooksInOrder());
                homeController.resetShoppingCart(user);
                // Reduce stock quantities by cart quantities
                for (BookEntity b : cart.keySet()) {
                    b.addStockQuantity(-(cart.get(b)));
                    bookCommandService.save(b);
                    // Restocking is handled by database triggers
                }

                return "redirect:/user/profile";
            } catch (EntityExistsException e) {
                bindingResult.rejectValue("order", "error.order", "Order could not be created");
                log.error(e.getMessage());
            } catch (NotFoundException e) {
                bindingResult.rejectValue("user", "error.user", "User could not be authenticated");
                log.error(e.getMessage());
            }
        } else {
            bindingResult.reject("globalError", "Order could not be placed, make sure all fields are filled.");
        }
        model.addAttribute("orderForm", orderForm);
        return "order";
    }
}
