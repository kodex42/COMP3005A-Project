package comp.A.project.controllers;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.AddressUpdateForm;
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
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private AddressQueryService addressQueryService;
    @Autowired
    private AddressCommandService addressCommandService;
    @Autowired
    private BookQueryService bookQueryService;

    public Iterable<UserEntity> getAllUsers() {
        return userQueryService.getAllUsers();
    }

    public UserEntity getCurrentUser(Principal principal) throws NotFoundException {
        return userQueryService.getByUsername(principal.getName());
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        log.info("Request: view user profile");

        // Is user authenticated?
        if (principal != null) {
            try {
                UserEntity user = getCurrentUser(principal);
                model.addAttribute("user", user);
                model.addAttribute("orders", user.getOrders());
                model.addAttribute("addressUpdateForm", new AddressUpdateForm(user));
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
    public String addUserSavedAddress(@Valid AddressUpdateForm addressUpdateForm, BindingResult bindingResult, Model model, Principal principal) {
        log.info("Request: save addresses");

        UserEntity user = null;
        // Is user authenticated?
        if (principal != null) {
            try {
                user = getCurrentUser(principal);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        if (user != null && !bindingResult.hasErrors()) {
            user.setBillingAddress(addressCommandService.create(addressUpdateForm.getBillingAddress()));
            user.setShippingAddress(addressCommandService.create(addressUpdateForm.getShippingAddress()));
            userCommandService.saveUser(user);
        }
        else if (user != null && bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("orders", user.getOrders());
            model.addAttribute("addressUpdateForm", addressUpdateForm);
            return "user_profile";
        }

        return "redirect:/user/profile";
    }

    @PostMapping("/cart")
    public String addBookToUserCart(@RequestParam(value = "ISBN") String isbn, @RequestParam(value = "qty") Integer qty, Principal principal, HttpServletRequest request) {
        log.info("Request: add book " + isbn + " to current user's cart");

        // Is user authenticated?
        if (principal != null) {
            try {
                UserEntity user = getCurrentUser(principal);
                BookEntity book = bookQueryService.getBook(isbn);
                homeController.addToCart(user, book, qty);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/cart")
    public String RemoveBookFromUserCart(@RequestParam(value = "ISBN") String isbn, @RequestParam(value = "qty") Integer qty, Principal principal, HttpServletRequest request) {
        log.info("Request: remove book " + isbn + " from current user's cart");

        // Is user authenticated?
        if (principal != null) {
            try {
                UserEntity user = getCurrentUser(principal);
                BookEntity book = bookQueryService.getBook(isbn);
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
