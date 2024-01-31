-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS bank;

-- Selecionar o banco de dados
USE bank;

-- Criar a tabela de usuários
CREATE TABLE users (
    id bigint not null AUTO_INCREMENT primary key,
    name varchar(100) not null,
    age integer not null,
    phone varchar(20) not null,
    address varchar(60) not null,
    cpf varchar(15) not null,
    password varchar(15) not null
);

-- Criar a tabela de contas
CREATE TABLE accounts (
    id bigint not null AUTO_INCREMENT primary key,
    accountNumber varchar(20) not null unique,
    accountType varchar(20) not null,
    balance double not null,
    userId bigint not null,
    foreign key (userId) references users(id)
);


-- Selecionar todos os registros das tabelas
SELECT * FROM users;
SELECT * FROM accounts;

