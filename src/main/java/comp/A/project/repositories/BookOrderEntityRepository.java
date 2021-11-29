package comp.A.project.repositories;

import comp.A.project.DAO.BookOrderEntity;
import comp.A.project.DAO.BookOrderEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookOrderEntityRepository extends CrudRepository<BookOrderEntity, BookOrderEntityId> {

    @Query(value = "SELECT * FROM book_order WHERE ISBN = ?1", nativeQuery = true)
    Iterable<BookOrderEntity> getAllWithIsbn(String isbn);
}
