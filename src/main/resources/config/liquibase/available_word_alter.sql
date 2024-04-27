alter table wordl_available_word
    rename to wordl_word;

alter table wordl_word
    add column used date;