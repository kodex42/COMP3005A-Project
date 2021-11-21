drop table if exists address_info;
drop table if exists "user";
drop table if exists publisher;
drop table if exists "order";
drop table if exists purchase;
drop table if exists book;
drop table if exists book_order;

create table address
	(address_id		serial,
	 "name"			varchar(25),
	 province		varchar(25),
	 town			varchar(25),
	 postal_code	varchar(7),
	 street_address varchar(50),
	 primary key (address_id)
	);

create table "user"
	(username				varchar(25),
	 "password"				varchar(50),
	 user_type				varchar(10),
	 billing_address		int,
	 shipping_address		int,
	 primary key (username),
	 foreign key (billing_address) references address_info (address_id)
	 	on delete set null,
	 foreign key (shipping_address) references address_info (address_id)
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
	 foreign key (billing_address) references address_info (address_id)
	 	on delete set null,
	 foreign key (shipping_address) references address_info (address_id)
	 	on delete set null
	);

create table publisher
	(publisher_id		serial,
	 address			int,
	 email				varchar(50),
	 phone				varchar(12),
	 bank_account_no	varchar(25),
	 primary key (publisher_id),
	 foreign key (address) references address_info (address_id)
	 	on delete set null
	);

create table book
	(ISBN					varchar(13),
	 title					varchar(25),
	 author_name			varchar(25),
	 publisher_id			int,
	 genre					varchar(15),
	 pages					smallint,
	 price					numeric(6, 2),
	 publisher_percentage	numeric(3, 2) check (publisher_percentage >= 0),
	 stock_quantity			int,
	 primary key (ISBN),
	 foreign key (publisher_id) references publisher
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
	 	on delete set null
	);