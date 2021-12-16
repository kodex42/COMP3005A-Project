package comp.A.project.controllers;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.BookForm;
import comp.A.project.services.command.BookCommandService;
import comp.A.project.services.command.PurchaseCommandService;
import comp.A.project.services.query.BookQueryService;
import comp.A.project.services.query.PublisherQueryService;
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

/*  OrderController

    Handles the following routes:
        GET
            /book?isbn=
            /book/new
        POST
            /book/create
        DELETE
            /book/remove?isbn
*/
@Controller
@RequestMapping("/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private HomeController homeController;
    @Autowired
    private UserController userController;
    @Autowired
    private BookQueryService bookQueryService;
    @Autowired
    private PublisherQueryService publisherQueryService;
    @Autowired
    private BookCommandService bookCommandService;
    @Autowired
    private PurchaseCommandService purchaseCommandService;

    public BookEntity getBookByISBN(String isbn) throws NotFoundException {
        return bookQueryService.getBook(isbn);
    }

    @GetMapping("")
    public String viewBook(@RequestParam(value = "isbn") String isbn, Model model, Principal principal) {
        log.info("Request: view book " + isbn);

        try {
            BookEntity bookEntity = getBookByISBN(isbn);
            UserEntity user = userController.getCurrentUser(principal);
            model.addAttribute("book", bookEntity);
            model.addAttribute("user", user);
            model.addAttribute("cart", homeController.getShoppingCart(user));
            return "book";
        } catch (NotFoundException e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/new")
    public String getBookForm(Model model) {
        log.info("Request: view book form");

        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("publishers", publisherQueryService.getAllPublishers());
        return "create_book";
    }

    @PostMapping("/create")
    public String createBook(@Valid BookForm bookForm, BindingResult bindingResult, Model model) {
        log.info("Request: create book " + bookForm.getTitle());

        if (!bindingResult.hasErrors()) {
            try {
                BookEntity bookEntity = bookCommandService.create(bookForm);
                return "redirect:/book?isbn=" + bookForm.getISBN();
            } catch (NotFoundException e) {
                bindingResult.rejectValue("publisherName", e.getMessage());
            } catch (EntityExistsException e) {
                bindingResult.reject("globalError", e.getMessage());
            }
        }

        model.addAttribute("bookForm", bookForm);
        model.addAttribute("publishers", publisherQueryService.getAllPublishers());
        return "create_book";
    }

    @DeleteMapping("/remove")
    public String removeBook(@RequestParam(value = "ISBN") String isbn) {
        log.info("Request: remove book " + isbn);

        try {
            bookCommandService.remove(isbn);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
