package comp.A.project.repositories;

import comp.A.project.DAO.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    @Query(value = "SELECT COALESCE(SUM(total), 0) FROM \"order\"", nativeQuery = true)
    double sumTotals();

    @Query(value = "SELECT b.author_name, COALESCE(SUM(o.total), 0) FROM book b NATURAL JOIN book_order x NATURAL JOIN \"order\" o GROUP BY b.author_name", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByAuthorName();

    @Query(value = "SELECT b.genre, COALESCE(SUM(o.total), 0) FROM book b NATURAL JOIN book_order x NATURAL JOIN \"order\" o GROUP BY b.genre", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByGenre();

    @Query(value = "SELECT \"p\".name, COALESCE(SUM(o.total), 0) FROM book b INNER JOIN publisher \"p\" ON b.publisher_name = \"p\".name NATURAL JOIN book_order x NATURAL JOIN \"order\" o GROUP BY \"p\".name", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByPublisher();
}
