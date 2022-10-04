drop database if exists bank;

create database bank;

use bank;

create table account (
    acct_id CHAR(8) NOT NULL,
    balance DECIMAL(7, 2) NOT NULL,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(acct_id)
);