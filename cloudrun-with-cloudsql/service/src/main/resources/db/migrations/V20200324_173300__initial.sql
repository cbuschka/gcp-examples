create table message
(
    id   bigserial primary key,
    body text not null
);

insert into message ( body ) values ( 'Hooray, it works!' );
