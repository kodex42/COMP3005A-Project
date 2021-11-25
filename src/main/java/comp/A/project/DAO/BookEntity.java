package comp.A.project.DAO;

import comp.A.project.forms.BookForm;
import comp.A.project.forms.UserForm;
import comp.A.project.services.User.PublisherQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "book", schema = "public")
public class BookEntity {
    @Transient
    @Autowired
    private PublisherQueryService publisherQueryService;

    @Id
    private String ISBN;
    private String title;
    private String authorName;
    @ManyToOne
    @JoinColumn(name = "publisher_name", nullable = false)
    private PublisherEntity publisher;
    private String genre;
    private int pages;
    private double price;
    private double publisherPercentage;
    private int stockQuantity;

    public BookEntity() {
        super();
    }

    public BookEntity(BookForm bookForm) throws NotFoundException {
        super();
        this.ISBN = bookForm.getISBN();
        this.title = bookForm.getTitle();
        this.authorName = bookForm.getAuthor();
        this.publisher = publisherQueryService.getByName(bookForm.getPublisherName());
        this.genre = bookForm.getGenre();
        this.pages = bookForm.getPages();
        this.price = bookForm.getPrice();
        this.publisherPercentage = bookForm.getPublisherPercentage();
        this.stockQuantity = bookForm.getStockQuantity();
    }
}
