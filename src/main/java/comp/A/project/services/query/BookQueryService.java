package comp.A.project.services.query;

import comp.A.project.DAO.BookEntity;
import comp.A.project.repositories.BookRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookQueryService {
    @Autowired
    private BookRepository bookRepository;

    public BookEntity getBook(String ISBN) throws NotFoundException {
        return bookRepository.findByISBN(ISBN).orElseThrow(() -> new NotFoundException("Book with specified ISBN not found"));
    }

    public Iterable<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<BookEntity> getAllWithTitleContaining(String title) {
        return bookRepository.findAllWithTitleContaining(title);
    }

    public Iterable<BookEntity> getAllBooksWithGenre(String genre) {
        return bookRepository.findAllByGenre(genre);
    }

    public Iterable<BookEntity> getAllByAuthorName(String authorName) {
        return bookRepository.findAllByAuthorName(authorName);
    }

    public Iterable<BookEntity> getAllByPublisherName(String publisherName) {
        return bookRepository.findAllByPublisherName(publisherName);
    }

    public Iterable<BookEntity> getAllInPriceRange(double low, double high) {
        return bookRepository.findAllInPriceRange(low, high);
    }
}
