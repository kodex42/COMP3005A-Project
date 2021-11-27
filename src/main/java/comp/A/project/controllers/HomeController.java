package comp.A.project.controllers;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.UserForm;
import comp.A.project.services.query.BookQueryService;
import comp.A.project.services.query.OrderQueryService;
import comp.A.project.services.query.PublisherQueryService;
import comp.A.project.services.query.PurchaseQueryService;
import comp.A.project.services.query.UserQueryService;
import javassist.NotFoundException;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*  HomeController

    Handles the following routes:
        GET
            /
            /index
            /login
            /register
            /admin
*/
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private PublisherQueryService publisherQueryService;
    @Autowired
    private BookQueryService bookQueryService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private PurchaseQueryService purchaseQueryService;

    private final Map<String, Map<BookEntity, Integer>> shoppingCarts = new PassiveExpiringMap<>(10, TimeUnit.MINUTES);

    @GetMapping({"/", "/index"})
    public String getHome(Model model, Principal principal) {
        log.info("Request: index");

        // Is user authenticated?
        if (principal != null) {
            try {
                // Get user data
                UserEntity user = userQueryService.getByUsername(principal.getName());
                model.addAttribute("user", user);
                model.addAttribute("cart", this.shoppingCarts.get(user.getUsername()));
                // Could add more cases as more user types are created if there is time
                if ("ADMIN".equals(user.getAuthority())) {
                    return getAdmin(model);
                }
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("books", bookQueryService.getAllBooks());
        return "home";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        log.info("Request: login");

        return "login";
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute UserForm userForm) {
        log.info("Request: register");

        return "register";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        log.info("Request: admin");

        model.addAttribute("users", userQueryService.getAllUsers());
        model.addAttribute("publishers", publisherQueryService.getAllPublishers());
        model.addAttribute("books", bookQueryService.getAllBooks());
        model.addAttribute("purchases", purchaseQueryService.getAllPurchases());
        model.addAttribute("orders", orderQueryService.getAllOrders());
        double expenses = purchaseQueryService.getTotalExpenses();
        double income = orderQueryService.getTotalIncome();
        model.addAttribute("expenses", expenses);
        model.addAttribute("income", income);
        model.addAttribute("profit", income - expenses);
        return "admin";
    }

    public void addToCart(UserEntity user, BookEntity book, int qty) {
        if (!this.shoppingCarts.containsKey(user.getUsername()))
            this.shoppingCarts.put(user.getUsername(), new HashMap<>());
        Map<BookEntity, Integer> cart = new HashMap<>(Map.copyOf(this.shoppingCarts.get(user.getUsername())));
        if (!cart.containsKey(book) || cart.get(book) + qty <= book.getStockQuantity()) {
            cart.merge(book, qty, Integer::sum);
            if (cart.get(book) <= 0)
                cart.remove(book);
            this.shoppingCarts.put(user.getUsername(), cart);
        }
    }

    public void removeFromCart(UserEntity user, BookEntity book, int qty) {
        if (!this.shoppingCarts.containsKey(user.getUsername()) || !this.shoppingCarts.get(user.getUsername()).containsKey(book))
            return;
        Map<BookEntity, Integer> cart = new HashMap<>(Map.copyOf(this.shoppingCarts.get(user.getUsername())));
        cart.merge(book, -qty, Integer::sum);
        if (cart.get(book) <= 0)
            cart.remove(book);
        this.shoppingCarts.put(user.getUsername(), cart);
    }

    public Map<BookEntity, Integer> getShoppingCart(UserEntity user) {
        return this.shoppingCarts.get(user.getUsername());
    }
}
