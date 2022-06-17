-- Refresh tables
drop table if exists clients cascade;
drop table if exists accounts cascade;

create table if not exists clients (
	id serial primary key,
	first_name varchar not null,
	last_name varchar not null
);

create table if not exists accounts (
	id serial primary key,
	client_id int references clients(id)
		on delete cascade,
	type varChar not null,
	balance decimal not null
);

-- Initial data set
insert into clients values
(default, 'Alice', 'Apple'),
(default, 'Bob', 'Bacon'),
(default, 'Carl', 'Cake');

insert into accounts values
(default, 1, 'CHECKING', 50),
(default, 1, 'SAVINGS', 100),
(default, 2, 'CHECKING', 150),
(default, 3, 'SAVINGS', 200);

-- View data
select * from clients;
select * from accounts;
