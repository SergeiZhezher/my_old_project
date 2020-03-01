
create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table comment (
id integer not null,
comment varchar(512),
film_id integer,
user_id integer,
primary key (id)
);

create table film (
id integer not null,
description varchar(2048),
film_name varchar(255),
genre varchar(255),
link_full varchar(255),
link_trailer varchar(255),
rait float,
votes float,
years integer,
primary key (id)
);

create table user (
id integer not null,
activation_code varchar(255),
active bit,
email varchar(255),
password varchar(255),
username varchar(255),
primary key (id)
);

create table user_role (user_id integer not null, roles varchar(255));
alter table comment add constraint FK8kcum44fvpupyw6f5baccx25c foreign key (user_id) references user (id);
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id);