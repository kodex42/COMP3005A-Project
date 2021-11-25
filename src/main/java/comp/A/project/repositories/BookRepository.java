package comp.A.project.repositories;

import comp.A.project.DAO.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<BookEntity, String> {

    Optional<BookEntity> findByISBN(String ISBN);

    @Query(value = "SELECT * FROM book WHERE title LIKE ?1", nativeQuery = true)
    Iterable<BookEntity> findAllWithTitleContaining(String title);

    Iterable<BookEntity> findAllByGenre(String genre);

    Iterable<BookEntity> findAllByAuthorName(String authorName);

    Iterable<BookEntity> findAllByPublisherName(String publisherName);

    @Query(value = "SELECT * FROM book WHERE price BETWEEN ?1 AND ?2", nativeQuery = true)
    Iterable<BookEntity> findAllInPriceRange(double low, double high);
}
