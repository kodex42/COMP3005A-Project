package comp.A.project.controllers;

import comp.A.project.DAO.OrderEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.OrderForm;
import comp.A.project.forms.UserForm;
import comp.A.project.services.command.OrderCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

/*  OrderController

    Handles the following routes:
        GET
            /order
        POST
            /order

*/
@Controller
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private HomeController homeController;
    @Autowired
    private OrderCommandService orderCommandService;

    @GetMapping("/")
    public String getOrder(Model model) {
        log.info("Request: view order");

        model.addAttribute("orderForm", new OrderForm());
        return "order";
    }

    @PostMapping("/")
    public String createOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        log.info("Request: create order for " + orderForm.getUser().getUsername());

        if (!bindingResult.hasErrors()) {
            try {
                OrderEntity order = orderCommandService.createOrder(orderForm);
                return "redirect:/user/profile";
            } catch (EntityExistsException e) {
                bindingResult.rejectValue("order", "error.order", "That username is already taken");
                log.error(e.getMessage());
            }
        }
        return "order";
    }
}
