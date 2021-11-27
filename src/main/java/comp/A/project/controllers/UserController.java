package comp.A.project.controllers;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.UserForm;
import comp.A.project.services.query.BookQueryService;
import comp.A.project.services.command.UserCommandService;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

/*  UserController

    Handles the following routes:
        POST
            /user/create
            /user/cart
            /user/confirm_cart
*/
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private HomeController homeController;
    @Autowired
    private OrderController orderController;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private BookQueryService bookQueryService;

    @PostMapping("/create")
    public String createUser(@Valid UserForm userForm, BindingResult bindingResult) {
        log.info("Request: create user " + userForm.getUsername());

        if (!bindingResult.hasErrors()) {
            try {
                UserEntity user = userCommandService.createUser(userForm);

                return "redirect:/login";
            } catch (EntityExistsException e) {
                bindingResult.rejectValue("username", "error.user", "That username is already taken");
                log.error(e.getMessage());
            }
        }

        return "register";
    }

    @PostMapping("/cart")
    public String addBookToUserCart(@RequestParam(value = "ISBN") String ISBN, @RequestParam(value = "qty") Integer qty, Principal principal, HttpServletRequest request) {
        log.info("Request: add book " + ISBN + " to current user's cart");

        // Is user authenticated?
        if (principal != null) {
            try {
                UserEntity user = userQueryService.getByUsername(principal.getName());
                BookEntity book = bookQueryService.getBook(ISBN);
                homeController.addToCart(user, book, qty);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/cart")
    public String RemoveBookFromUserCart(@RequestParam(value = "ISBN") String ISBN, @RequestParam(value = "qty") Integer qty, Principal principal, HttpServletRequest request) {
        log.info("Request: remove book " + ISBN + " from current user's cart");

        // Is user authenticated?
        if (principal != null) {
            try {
                UserEntity user = userQueryService.getByUsername(principal.getName());
                BookEntity book = bookQueryService.getBook(ISBN);
                homeController.removeFromCart(user, book, qty);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/confirm_cart")
    public String confirmCart(Model model, Principal principal) throws NotFoundException {
        log.info("Request: cart has been confirmed");

        UserEntity user = userQueryService.getByUsername(principal.getName());
        Map<BookEntity, Integer> cart = homeController.getShoppingCart(user);

        double total = 0;
        for (BookEntity b : cart.keySet())
            total += b.getPrice() * cart.get(b);

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("user", user);
        return orderController.getOrder(model);
    }
}
