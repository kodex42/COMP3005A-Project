-- Queries automated by CrudRepositories for all entities
SELECT * FROM <table>;
SELECT * FROM <table> WHERE id = <id>;
SELECT * FROM <table1> NATURAL JOIN <table2> WHERE id = <id>;
DELETE FROM <table> WHERE id = <id>;
UPDATE <table> SET (...) WHERE id = <id>;

-- Custom queries written by me
-- Searches for an address matching a given postal_code, street_address, province, and town to ensure no duplicate addresses with different IDs are created
SELECT a.address_id, a."name", a.postal_code, a.street_address FROM address a NATURAL JOIN postal_region p WHERE a.postal_code=?1 AND a.street_address=?2 AND p.province=?3 AND p.town=?4 LIMIT 1
-- Query to filter Books based on submitted form data using the LIKE comparator and % operator to only capture tuples with attributes that contain matching substrings
SELECT * FROM book WHERE isbn LIKE %?1% AND LOWER(title) LIKE %?2% AND LOWER(author_name) LIKE %?3% AND LOWER(publisher_name) LIKE %?4% AND LOWER(genre) LIKE %?5% AND pages BETWEEN ?6 AND ?7 AND price BETWEEN ?8 AND ?9
-- Searches for Book sales from the book_order_totals view grouped by author_name
SELECT author_name, COALESCE(SUM(price * quantity), 0) FROM book_order_totals GROUP BY author_name
-- Searches for Book sales from the book_order_totals view grouped by genre
SELECT genre, COALESCE(SUM(price * quantity), 0) FROM book_order_totals GROUP BY genre
-- Searches for Book sales from the book_order_totals view grouped by name
SELECT name, COALESCE(SUM(price * quantity), 0) FROM book_order_totals GROUP BY name
-- Searches for detailed Book sales from the book_order_totals view within a specified date range
SELECT isbn, price, quantity, order_no, COALESCE(SUM(price * quantity), 0) FROM book_order_totals WHERE date BETWEEN ?1 AND ?2 GROUP BY isbn, price, quantity, order_no
-- Computes total expenses
SELECT COALESCE(SUM(total_cost), 0) FROM purchase
-- Queries all purchases for a specific Book
SELECT * FROM purchase WHERE ISBN = ?1