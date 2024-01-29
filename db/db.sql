-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS banco;

-- Selecionar o banco de dados
USE banco;

-- Criar a tabela de usuários
CREATE TABLE users (
    id bigint not null AUTO_INCREMENT primary key,
    name varchar(100) not null,
    age integer not null,
    phone varchar(20) not null,
    address varchar(60) not null
);

-- Criar a tabela de contas
CREATE TABLE accounts (
    id bigint not null AUTO_INCREMENT primary key,
    account_number varchar(20) not null unique,
    accountType varchar(20) not null,
    balance double not null,
    userId bigint not null,
    foreign key (userId) references users(id)
);

-- Inserir dados na tabela 'users'
INSERT INTO users (name, age, phone, address, accountType) VALUES ('test1', 19, '19991144324', '12345-678 Cityville, 123 Street', CURRENT);

-- Selecionar todos os registros da tabela 'accounts' onde 'accountType' é 'CURRENT'
SELECT * FROM accounts WHERE accountType = 'CURRENT';
-- Selecionar todos os registros da tabela 'accounts' onde 'accountType' é 'SAVINGS'
SELECT * FROM accounts WHERE accountType = 'SAVINGS';

-- Selecionar todos os registros da tabela 'accounts'
SELECT * FROM accounts;

-- Excluir todos os registros da tabela 'accounts'
DELETE FROM accounts;

-- Desativar o modo de atualização segura
SET SQL_SAFE_UPDATES = 0;

-- Excluir todos os registros da tabela 'users'
DELETE FROM users;

-- Excluir todos os registros da tabela 'accounts'
DELETE FROM accounts;

-- Ativar o modo de atualização segura
SET SQL_SAFE_UPDATES = 1;
