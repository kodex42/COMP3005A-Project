package comp.A.project.services.command;

import comp.A.project.DAO.BookEntity;
import comp.A.project.DAO.BookOrderEntity;
import comp.A.project.DAO.PurchaseEntity;
import comp.A.project.controllers.HomeController;
import comp.A.project.forms.BookForm;
import comp.A.project.repositories.BookRepository;
import comp.A.project.services.query.BookOrderEntityQueryService;
import comp.A.project.services.query.BookQueryService;
import comp.A.project.services.query.PublisherQueryService;
import comp.A.project.services.query.PurchaseQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class BookCommandService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookQueryService bookQueryService;
    @Autowired
    private PublisherQueryService publisherQueryService;
    @Autowired
    private PurchaseQueryService purchaseQueryService;
    @Autowired
    private PurchaseCommandService purchaseCommandService;
    @Autowired
    private BookOrderEntityQueryService bookOrderEntityQueryService;
    @Autowired
    private BookOrderEntityCommandService bookOrderEntityCommandService;

    public BookEntity create(BookForm bookForm) throws NotFoundException, EntityExistsException {
        if (!bookQueryService.bookExists(bookForm.getISBN())) {
            try {
                BookEntity bookEntity = new BookEntity(bookForm);
                bookEntity.setPublisher(publisherQueryService.getByName(bookForm.getPublisherName()));
                return save(bookEntity);
            } catch (NotFoundException e) {
                throw new NotFoundException("Only books published by contracted publishers may be added to the system");
            }
        }
        else {
            throw new EntityExistsException("A book with the given ISBN already exists");
        }
    }

    public BookEntity save(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    public void remove(String isbn) throws NotFoundException {
        BookEntity bookEntity = bookRepository.findByISBN(isbn).orElseThrow(() -> new NotFoundException("Book with Specified ISBN could not be found"));
        // book_order and purchase entries will be removed, store profit and expense difference in dummy purchase row
        // This is not the best solution, but it keeps the database in good form
        Iterable<PurchaseEntity> purchases = purchaseQueryService.getAllWithISBN(isbn);
        for (PurchaseEntity p : purchases) {
            // Add total amount spent to dummy row
            HomeController.lostDataLosses = HomeController.lostDataLosses + p.getTotalCost();
            purchaseCommandService.remove(p);
        }
        Iterable<BookOrderEntity> bookOrders = bookOrderEntityQueryService.getAllWithIsbn(isbn);
        for (BookOrderEntity b : bookOrders) {
            // Subtract book specific income from book_orders and add book specific publisher cut to dummy row
            double income = b.getBook().getPrice() * b.getQuantity();
            double publisherCutLoss = income * b.getBook().getPublisherPercentage();
            HomeController.lostDataLosses = HomeController.lostDataLosses - income + publisherCutLoss;
            bookOrderEntityCommandService.remove(b);
        }
        bookRepository.delete(bookEntity);
    }
}
