package comp.A.project.controllers;

import comp.A.project.DAO.AddressEntity;
import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.AddressForm;
import comp.A.project.forms.UserForm;
import comp.A.project.services.command.AddressCommandService;
import comp.A.project.services.query.AddressQueryService;
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

/*  UserController

    Handles the following routes:
        GET
            /user/profile
        POST
            /user/create
            /user/add_address
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
    @Autowired
    private AddressQueryService addressQueryService;
    @Autowired
    private AddressCommandService addressCommandService;

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        log.info("Request: view user profile");

        // Is user authenticated?
        if (principal != null) {
            try {
                UserEntity user = userQueryService.getByUsername(principal.getName());
                model.addAttribute("user", user);
                model.addAttribute("orders", user.getOrders());
                model.addAttribute("billingAddressForm", new AddressForm(user.getBillingAddress()));
                model.addAttribute("shippingAddressForm", new AddressForm(user.getShippingAddress()));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return "user_profile";
    }

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

    @PostMapping("/add_address")
    public String addUserSavedAddress(@RequestParam(value = "type") String addressType, @Valid AddressForm addressForm, Model model, Principal principal) {
        log.info("Request: save address " + addressForm.getName() + " as current user's saved " + addressType + " address");

        UserEntity user = null;
        // Is user authenticated?
        if (principal != null) {
            try {
                user = userQueryService.getByUsername(principal.getName());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        if (user != null) {
            if (addressType.equals("billing"))
                user.setBillingAddress(addressCommandService.create(addressForm));
            else if (addressType.equals("shipping"))
                user.setShippingAddress(addressCommandService.create(addressForm));
            userCommandService.saveUser(user);
        }

        return "redirect:/user/profile";
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
    public String confirmCart() {
        log.info("Request: cart has been confirmed");

        return "redirect:/order";
    }
}
