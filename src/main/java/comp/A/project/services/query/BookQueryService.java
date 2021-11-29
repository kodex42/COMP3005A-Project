package comp.A.project.services.query;

import comp.A.project.DAO.BookEntity;
import comp.A.project.forms.BookFilterForm;
import comp.A.project.repositories.BookRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class BookQueryService {
    @Autowired
    private BookRepository bookRepository;

    public BookEntity getBook(String ISBN) throws NotFoundException {
        return bookRepository.findByISBN(ISBN).orElseThrow(() -> new NotFoundException("Book with specified ISBN not found"));
    }

    public boolean bookExists(String isbn) {
        return bookRepository.findByISBN(isbn).isPresent();
    }

    public Iterable<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<BookEntity> getFilteredBooks(BookFilterForm bookFilterForm) {
        return bookRepository.getFilteredBooks(
                bookFilterForm.getISBN(),
                bookFilterForm.getTitle().toLowerCase(),
                bookFilterForm.getAuthor().toLowerCase(),
                bookFilterForm.getPublisher().toLowerCase(),
                bookFilterForm.getGenre().toLowerCase(),
                bookFilterForm.getPagesMin(),
                bookFilterForm.getPagesMax(),
                bookFilterForm.getPriceMin(),
                bookFilterForm.getPriceMax()
        );
    }
}
