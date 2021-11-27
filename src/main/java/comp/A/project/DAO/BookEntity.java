package comp.A.project.DAO;

import comp.A.project.forms.BookForm;
import comp.A.project.services.query.PublisherQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book", schema = "public")
public class BookEntity implements Comparable {
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

    @ManyToMany(mappedBy = "booksInOrder")
    private List<OrderEntity> ordersWithBook;

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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPublisherPercentage() {
        return publisherPercentage;
    }

    public void setPublisherPercentage(double publisherPercentage) {
        this.publisherPercentage = publisherPercentage;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public int hashCode() {
        return this.ISBN.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        return Integer.valueOf(this.ISBN.hashCode()).equals(((BookEntity) obj).ISBN.hashCode());
    }

    @Override
    public int compareTo(Object o) {
        if (getClass() != o.getClass())
            return 0;
        return this.title.compareTo(((BookEntity) o).title);
    }
}
