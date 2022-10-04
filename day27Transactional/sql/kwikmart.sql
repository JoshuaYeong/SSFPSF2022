-- drop database if exists
drop schema if exists kwikmart;

-- create database
create schema kwikmart;

use kwikmart;

create table po (
    ord_id int not null auto_increment,
    name varchar(64) not null,
    email varchar(64),
    
    primary key(ord_id)
);

create table line_item (
    item_id int not null auto_increment,
    description varchar(64),
    quantity int default '1',
    unit_price decimal(14,4),

    -- to be made into foreign keys
    ord_id int,

    primary key(item_id),

    constraint fk_ord_id
        foreign key(ord_id)
            references po(ord_id)
);