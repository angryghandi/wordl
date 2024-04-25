create table wordl_available_word
(
    id   serial primary key,
    word varchar(5) not null unique
);