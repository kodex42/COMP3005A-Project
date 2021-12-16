package comp.A.project.repositories;

import comp.A.project.DAO.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Query(value = "SELECT author_name, COALESCE(SUM(price * quantity), 0) FROM book_order_totals GROUP BY author_name", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByAuthorName();

    @Query(value = "SELECT genre, COALESCE(SUM(price * quantity), 0) FROM book_order_totals GROUP BY genre", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByGenre();

    @Query(value = "SELECT name, COALESCE(SUM(price * quantity), 0) FROM book_order_totals GROUP BY name", nativeQuery = true)
    Iterable<Object[]> orderTotalsGroupedByPublisher();

    @Query(value = "SELECT isbn, price, quantity, order_no, COALESCE(SUM(price * quantity), 0) FROM book_order_totals WHERE date BETWEEN ?1 AND ?2 GROUP BY isbn, price, quantity, order_no", nativeQuery = true)
    Iterable<Object[]> orderTotalsWithinDateRange(Timestamp t1, Timestamp t2);
}
