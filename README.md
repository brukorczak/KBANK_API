# API do BANCO

Esta é uma API para gerenciar usuários e operações bancárias. A API utiliza um banco de dados MySQL chamado `bank` com duas tabelas: `users` para armazenar informações dos usuários e `accounts` para registrar informações e operações bancárias.

Essa API faz integração com o front de KBANK, o front está disponível em [GitHub](https://github.com/brukorczak/KBANK_FRONT.git).

## Executando o aplicativo no dev mode

Você pode executar seu aplicativo no modo de desenvolvimento que permite live coding usando:
```shell script
./mvnw compile quarkus:dev
```
ou
```shell script
quarkus dev
```

> **_NOTE:_**  O Quarkus agora vem com uma Dev UI, que está disponível no dev mode apenas em http://localhost:8080/api/q/dev/.

## Swagger

A documentação da API pode ser acessada usando o Swagger. Após iniciar a aplicação, vá para:

http://localhost:8080/api/q/swagger-ui/#/

Lá, você encontrará uma interface interativa que detalha todos os endpoints, parâmetros e respostas da API. Isso facilita a compreensão e teste dos recursos disponíveis.

## Endpoints da API

### User Endpoints
- Lista todos os usuários cadastrados. `GET api/v1/users`
- Adiciona um novo usuário. `POST api/v1/users`
- Realiza o login de um usuário. `POST api/v1/users/login`
- Exclui um usuário pelo ID.`DELETE api/v1/users/{id}` 
- Atualiza as informações de um usuário pelo ID.`PUT api/v1/users/{id}`
- Retorna informações detalhadas de um usuário pelo ID. `GET api/v1/users/{id}/info`

### Bank Endpoints
-  Cria uma nova conta para o usuário. `POST api/v1/users/{id}/accounts`
- Lista os saldos das contas do usuário.`GET api/v1/users/balances/{id}`
- Realiza um depósito em uma conta.`POST api/v1/users/deposit` 
- Realiza um saque em uma conta.`POST api/v1/users/withdraw` 
- Realiza uma transferência entre contas. `PATCH api/v1/users/transfer`

## Exemplos de Uso

### Criar uma nova conta para um usuário
```http
POST /api/v1/users/{id}/accounts
```
### Listar saldos das contas de um usuário
```http
GET /api/v1/users/balances/{id}
```
### Realizar um depósito em uma conta
```http
POST /api/v1/users/deposit
```

> **OBS:_**  Lembre-se de substituir {id} pelos valores correspondentes. Este documento fornece uma visão geral do projeto, suas configurações e endpoints disponíveis. Para informações detalhadas sobre os parâmetros necessários em cada endpoint, consulte a documentação fornecida pelo Swagger UI.
