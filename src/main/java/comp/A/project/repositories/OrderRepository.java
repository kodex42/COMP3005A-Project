package comp.A.project.repositories;

import comp.A.project.DAO.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Query(value = "SELECT b.author_name, COALESCE(SUM(b.price * o.quantity), 0) FROM book b NATURAL JOIN book_order o GROUP BY b.author_name", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByAuthorName();

    @Query(value = "SELECT b.genre, COALESCE(SUM(b.price * o.quantity), 0) FROM book b NATURAL JOIN book_order o GROUP BY b.genre", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByGenre();

    @Query(value = "SELECT \"p\".name, COALESCE(SUM(b.price * o.quantity), 0) FROM book b INNER JOIN publisher \"p\" ON b.publisher_name = \"p\".name NATURAL JOIN book_order o GROUP BY \"p\".name", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByPublisher();
}
