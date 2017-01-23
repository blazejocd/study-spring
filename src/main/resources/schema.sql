create table Spitter (
	id identity,
	username varchar(20) unique not null,
	password varchar(150) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null
);

insert into Spitter (id, username,password, first_name,last_name,email) 
values (0,'admin','e191b360301690890620dbd7078fb589fd7c3fdd36f117c4706dd4b638280058219b4e5f016e4e44','Abraham','Lincoln','lincoln@edu.pl');