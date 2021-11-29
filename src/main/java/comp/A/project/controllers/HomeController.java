package comp.A.project.controllers;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.BookFilterForm;
import comp.A.project.forms.UserForm;
import comp.A.project.services.query.*;
import javassist.NotFoundException;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    public static double lostDataLosses = 0.0;

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private PublisherQueryService publisherQueryService;
    @Autowired
    private BookQueryService bookQueryService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private BookOrderEntityQueryService bookOrderEntityQueryService;
    @Autowired
    private PurchaseQueryService purchaseQueryService;

    private final Map<String, Map<BookEntity, Integer>> shoppingCarts = new PassiveExpiringMap<>(10, TimeUnit.MINUTES);

    @GetMapping("/")
    public String getHome(@Valid BookFilterForm bookFilterForm, BindingResult bindingResult, Model model, Principal principal) {
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
        Iterable<BookEntity> books;
        if (bookFilterForm == null) {
            books = bookQueryService.getAllBooks();
            model.addAttribute("filters", new BookFilterForm());
        }
        else {
            books = bookQueryService.getFilteredBooks(bookFilterForm);
            model.addAttribute("filters", bookFilterForm);
        }
        model.addAttribute("books", books);
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
        return "admin";
    }

    @GetMapping("/finances")
    public String getFinances(Model model) {
        log.info("Request: finances");

        model.addAttribute("sales_by_publisher", orderQueryService.getOrderTotalsGroupedByPublisher());
        model.addAttribute("sales_by_genre", orderQueryService.getOrderTotalsGroupedByGenre());
        model.addAttribute("sales_by_author", orderQueryService.getOrderTotalsGroupedByAuthorName());
        double expenses = purchaseQueryService.getTotalExpenses();
        double income = bookOrderEntityQueryService.getTotalIncome();
        double publisherCuts = bookOrderEntityQueryService.getTotalPublisherCut();
        model.addAttribute("expenses", expenses);
        model.addAttribute("income", income);
        model.addAttribute("publisher_cuts", publisherCuts);
        model.addAttribute("lost_data_losses", lostDataLosses);
        model.addAttribute("profit", income - publisherCuts - expenses - lostDataLosses);
        return "finances";
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

    public void resetShoppingCart(UserEntity user) {
        this.shoppingCarts.put(user.getUsername(), new HashMap<>());
    }
}
