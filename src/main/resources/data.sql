delete from postal_region;
delete from address;
delete from "user";
delete from publisher;
delete from "order";
delete from book;
delete from book_order;
delete from purchase;

insert into "user"
values ('admin', 'admin', 'ADMIN');
insert into "user"
values ('user', 'user', 'USER');