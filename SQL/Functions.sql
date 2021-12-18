-- Adds a new row to Purchase when a new Book is created. 50% publisher discount on retail price is assumed.
create or replace function init_purchase()
    returns trigger
    as $$
	begin
	   insert into purchase
       values ((select ISBN from book where ISBN = NEW.ISBN), NOW(), (select stock_quantity from book where ISBN = NEW.ISBN), (select price from book where ISBN = NEW.ISBN)*(select stock_quantity from book where ISBN = NEW.ISBN)/2);
	   return NEW;
	end;
    $$
	language 'plpgsql';

-- Adds a new row to Purchase when an existing Book's stock quantity falls below desirable threshold. 50% publisher discount on retail price is assumed.
create or replace function restock()
    returns trigger
    as $$
	begin
       update book
       set stock_quantity = stock_quantity + 10
       where ISBN = NEW.ISBN;
	   insert into purchase
       values ((select ISBN from book where ISBN = NEW.ISBN), NOW(), 10, (select price from book where ISBN = NEW.ISBN)*5);
	   return NEW;
	end;
    $$
	language 'plpgsql';