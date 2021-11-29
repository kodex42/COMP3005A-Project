package comp.A.project.forms;

import comp.A.project.DAO.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookFilterForm {
    private String ISBN = "";
    private String title = "";
    private String author = "";
    private String publisher = "";
    private String genre = "";
    private int pagesMin = 0;
    private int pagesMax = 1000;
    private double priceMin = 0.00;
    private double priceMax = 1000.0;

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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPagesMax() {
        return pagesMax;
    }

    public void setPagesMax(int pagesMax) {
        this.pagesMax = pagesMax;
    }

    public int getPagesMin() {
        return pagesMin;
    }

    public void setPagesMin(int pagesMin) {
        this.pagesMin = pagesMin;
    }

    public double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(double priceMax) {
        this.priceMax = priceMax;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
    }
}
