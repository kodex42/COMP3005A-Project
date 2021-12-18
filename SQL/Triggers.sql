drop trigger if exists book_stock_low_after_order on book;
drop trigger if exists book_initial_stockup on book;

-- Fires when a new book is added to the system
create trigger book_initial_stockup
    after insert on book
	for each row
    execute procedure init_purchase();

-- Fires when a book's stock quantity is less than 3 after an order is made
create trigger book_stock_low_after_order
    after update on book
	for each row
    when (NEW.stock_quantity <= 2)
    execute procedure restock();