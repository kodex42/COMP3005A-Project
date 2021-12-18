drop table if exists postal_region cascade;
drop table if exists address cascade;
drop table if exists "user" cascade;
drop table if exists publisher cascade;
drop table if exists "order" cascade;
drop table if exists purchase cascade;
drop table if exists book cascade;
drop table if exists book_order cascade;
drop view if exists book_order_totals;



create table postal_region
	(postal_code	varchar(7),
	 province		varchar(25),
	 town			varchar(25),
	 primary key (postal_code)
	);

create table address
	(address_id		serial,
	 "name"			varchar(25),
	 postal_code	varchar(7),
	 street_address varchar(50),
	 primary key (address_id),
	 foreign key (postal_code) references postal_region
	 	on delete cascade
	);

create table "user"
	(username				varchar(25),
	 "password"				varchar(50),
	 user_type				varchar(10),
	 billing_address		int,
	 shipping_address		int,
	 primary key (username),
	 foreign key (billing_address) references address (address_id)
	 	on delete set null,
	 foreign key (shipping_address) references address (address_id)
	 	on delete set null
	);

create table "order"
	(order_no				serial,
	 username				varchar(25),
	 "location"				varchar(30),
	 status					varchar(100),
	 "date"					timestamp,
	 billing_address		int,
	 shipping_address		int,
	 total					numeric(10, 2),
	 primary key (order_no),
	 foreign key (username) references "user"
	 	on delete set null,
	 foreign key (billing_address) references address (address_id)
	 	on delete set null,
	 foreign key (shipping_address) references address (address_id)
	 	on delete set null
	);

create table publisher
	(name				varchar(25),
	 address			int,
	 email				varchar(50),
	 phone				varchar(12),
	 bank_account_no	varchar(25),
	 primary key (name),
	 foreign key (address) references address (address_id)
	 	on delete set null
	);

create table book
	(ISBN					varchar(13),
	 title					varchar(75),
	 author_name			varchar(50),
	 publisher_name			varchar(25),
	 genre					varchar(15),
	 pages					smallint check (pages >= 0),
	 price					numeric(6, 2) check (price >= 0),
	 publisher_percentage	numeric(3, 2) check (publisher_percentage >= 0),
	 stock_quantity			int check (stock_quantity >= 0),
	 primary key (ISBN),
	 foreign key (publisher_name) references publisher (name)
	 	on delete cascade
	);

create table book_order
	(order_no	int,
	 ISBN		varchar(13),
	 quantity	smallint,
	 primary key (order_no, ISBN),
	 foreign key (order_no) references "order"
	 	on delete cascade,
	 foreign key (ISBN) references book
	 	on delete cascade
	);

create table purchase
	(ISBN		varchar(13),
	 "date"		timestamp,
	 quantity	int,
	 total_cost	numeric(10, 2),
	 primary key (ISBN, "date", quantity),
	 foreign key (ISBN) references book
	 	on delete cascade
	);

create view book_order_totals
	as select o.order_no, b.isbn, b.author_name, "p".name, b.genre, b.price, bo.quantity, o.username, o.date, o.total from book b inner join publisher "p" on b.publisher_name = "p".name natural join book_order bo natural join "order" o;