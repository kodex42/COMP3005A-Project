package comp.A.project.repositories;

import comp.A.project.DAO.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<BookEntity, String> {

    Optional<BookEntity> findByISBN(String ISBN);

    @Query(value = "SELECT * FROM book WHERE price BETWEEN ?1 AND ?2", nativeQuery = true)
    Iterable<BookEntity> findAllInPriceRange(double low, double high);

    @Query(value = "SELECT * FROM book WHERE isbn LIKE %?1% AND LOWER(title) LIKE %?2% AND LOWER(author_name) LIKE %?3% AND LOWER(publisher_name) LIKE %?4% AND LOWER(genre) LIKE %?5% AND pages BETWEEN ?6 AND ?7 AND price BETWEEN ?8 AND ?9", nativeQuery = true)
    Iterable<BookEntity> getFilteredBooks(String isbn, String title, String author, String publisher, String genre, int pagesMin, int pagesMax, double priceMin, double priceMax);
}
