delete from postal_region;
delete from address;
delete from "user";
delete from purchase;
delete from publisher;
delete from "order";
delete from book;
delete from book_order;

-- Initial Users
insert into "user"
values ('admin', 'admin', 'ADMIN');
insert into "user"
values ('user', 'user', 'USER');

-- Initial Addresses and Publishers
insert into postal_region
values ('H2H 2L9', 'Quebec', 'Montreal');
insert into postal_region
values ('M5H 3G8', 'Ontario', 'Toronto');
insert into address ("name", postal_code, street_address)
values ('Bibliotheque Quebecoise', (select postal_code from postal_region where postal_code = 'H2H 2L9'), '4609, rue d''Iberville, 1er etage');
insert into address ("name", postal_code, street_address)
values ('Irwin Publishing', (select postal_code from postal_region where postal_code = 'M5H 3G8'), '14 Duncan Street, Suite 206');
insert into publisher
values ('Livres BQ', (select address_id from address where name = 'Bibliotheque Quebecoise'), 'courrier@bibliothequedequebec.qc.ca', '514 829-5558', '123456789');
insert into publisher
values ('Irwin Law', (select address_id from address where name = 'Irwin Publishing'), 'contact@irwinlaw.com', '416 862-7690', '987654321');

-- Initial Books
insert into book
values ('9781552210659', 'The ABCs of Law School', 'Ramsay Ali; Daniel Batista', (select name from publisher where name = 'Irwin Law'), 'Law', 160, 26.95, 0.10, 10);
insert into book
values ('9781552214169', 'The Abridged Wigmore on Alcohol', 'James G. Wigmore', (select name from publisher where name = 'Irwin Law'), 'Law', 298, 49.95, 0.12, 10);
insert into book
values ('9781552213742', 'Advancing Social Rights in Canada', 'Martha Jackman; Bruce Porter', (select name from publisher where name = 'Irwin Law'), 'Civil Rights', 442, 60.00, 0.18, 10);
insert into book
values ('9781552213377', '“At the Barricades”', 'Alan Borovoy', (select name from publisher where name = 'Irwin Law'), 'Law', 400, 34.95, 0.08, 10);
insert into book
values ('9781552215784', 'The Charter of Rights and Freedoms, 7th edition', 'Robert J. Sharpe; Kent Roach', (select name from publisher where name = 'Irwin Law'), 'Civil Rights', 608, 75.00, 0.20, 10);
insert into book
values ('9782894064498', 'Le lendemain n''est pas sans amour', 'Andree Maillet', (select name from publisher where name = 'Livres BQ'), 'Short Story', 184, 11.95, 0.15, 10);
insert into book
values ('9782894064702', 'Cheval Indien', 'Richard Wagamese', (select name from publisher where name = 'Livres BQ'), 'Novel', 272, 11.95, 0.05, 10);
insert into book
values ('9782894064733', 'Les desordres amoureux', 'Marie Demers', (select name from publisher where name = 'Livres BQ'), 'Novel', 256, 11.95, 0.12, 10);
insert into book
values ('9782894064719', 'Les confitures de coings', 'Jacques Ferron', (select name from publisher where name = 'Livres BQ'), 'Novel', 352, 14.95, 0.19, 10);
insert into book
values ('9782894064726', 'Le cerveau et la musique', 'Michel Rochon', (select name from publisher where name = 'Livres BQ'), 'Essay', 192, 10.95, 0.05, 10);