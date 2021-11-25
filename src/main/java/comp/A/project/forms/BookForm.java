package comp.A.project.forms;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class BookForm {
    @NotEmpty(message = "ISBN is required")
    private String ISBN;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Author name is required")
    private String author;
    @NotEmpty(message = "Publisher name is required")
    private String publisherName;
    private String genre;
    @Min(value = 0, message = "Number of pages cannot be negative")
    private int pages;
    @DecimalMin(value = "0.0", message = "Books cannot be free", inclusive = false)
    private double price;
    @DecimalMin(value = "0.0", message = "Publisher percentage must be positive", inclusive = false)
    private double publisherPercentage;
    @Min(value = 0, message = "Amount in stock cannot be negative")
    private int stockQuantity;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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
}