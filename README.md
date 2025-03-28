# Bookstore App

Desafio técnico NT Consult.

## Sobre o Projeto

API para cadastro, alteração, exclusão e consultas de livros.

## Tecnologias Utilizadas

- **Java** - Linguagem de programação utilizada.
- **Spring Framework** - Framework para criação da API.
- **JPA** - Para mapeamento objeto-relacional e persistência de dados.
- **H2 Database** - Banco de dados utilizado.
- **Docker** - Para containerização da aplicação.

## Funcionalidades

- Cadastro de livros.
- Exclusão de livros.
- Alteração de livros.
- Busca de livros (por ID, categoria autor).

## Instalação

### Siga os passos abaixo para executar o projeto localmente.

1. Clone o repositório:

   ```bash
   git clone https://github.com/comettimvitor/Bookstore-App.git
   
2. Abra o projeto na sua IDE de preferência e, no terminal da IDE, execute o comando:
   ```bash
   mvn clean package

3. Depois execute-o com:
   ```bash
   mvn spring-boot:run

### Siga os passos abaixo para baixar a imagem do projeto, criar o container e executá-lo via Docker.

1. Abra o terminal docker
2. Baixe a imagem do projeto:

   ```bash
   docker pull comettivitor/bookstore:latest

3. Crie o volume e o container docker:
    
   ```bash
   docker run -d -p 8080:8080 --name bookstore -v bookstore-app-h2:/app comettivitor/bookstore:latest

4. Confira se o projeto esta em execução:

   ```bash
   docker ps
   
5. Caso o container não esteja iniciado, basta iniciá-lo:

   ```bash
   docker start bookstore
   
6. Agora a API está pronta

## Endpoints da API

- **Observação**: Tanto na execução local, como na execução pelo docker a API funcionará em localhost e na porta 8080.



### 1. Cadastrar livro `POST /livro/cadastrar`

Exemplo de URL: `http://localhost:8080/livro/cadastrar`

#### Request
```
Content-type: application/json

{
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}
```

#### Responses
```
(200 OK)

{
   "id": 1,
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}

(409 CONFLICT)

{
    "error": "409",
    "message": "O livro O Senhor dos Anéis do autor J.R.R. Tolkien ja foi cadastrado."
}
```

### 2. Alterar cadastro `PUT /livro/alterar/{id}`

Exemplo de URL: `http://localhost:8080/livro/alterar/1`

#### Request
```
{
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}
```

#### Responses
```
(200 OK)
{
   "id": 1,
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}

(409 CONFLICT)
{
    "error": "409",
    "message": "O livro O Senhor dos Anéis do autor J.R.R. Tolkien ja foi cadastrado."
}
```

### 3. Busca todos os livros `GET /livro/buscar`

Exemplo de URL: `http://localhost:8080/livro/buscar`

#### Responses
```
(200 OK)
{
   {
      "id": 1,
      "titulo": "O Senhor dos Anéis",
      "autor": "J.R.R. Tolkien",
      "anoPublicacao": 1954,
      "categoria": "FANTASIA"
   },
   {
      "id": 2,
      "titulo": "O Hobbit",
      "autor": "J.R.R. Tolkien",
      "anoPublicacao": 1937,
      "categoria": "FANTASIA"
   }
}

(404 NOT FOUND)
{
    "error": "404",
    "message": "Livros não encontrados."
}
```

### 4. Busca livros por ID `GET /livro/buscar/{id}`

Exemplo de URL: `http://localhost:8080/livro/buscar/1`

#### Responses
```
(200 OK)
{
   "id": 1,
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}

(404 NOT FOUND)
{
    "error": "404",
    "message": "Livro não encontrado."
}
```

### 5. Busca livros por autor `GET /livro/buscar/autor`

- **Query Params**
  - Key: autor
  - Value: Tolkien

Exemplo de URL: `http://localhost:8080/livro/buscar/autor?autor=Tolkien`

#### Responses
```
(200 OK)
{
   "id": 1,
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}

(404 NOT FOUND)
{
    "error": "404",
    "message": "Livro não encontrado."
}
```

### 6. Busca livros por título `GET /livro/buscar/titulo`

- **Query Params**
   - Key: titulo
   - Value: O_Senhor_dos_Anéis

Exemplo de URL: `http://localhost:8080/livro/buscar/titulo?titulo=O_Senhor_dos_Anéis`

#### Responses
```
(200 OK)
{
   "id": 1,
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}

(404 NOT FOUND)
{
    "error": "404",
    "message": "Livro não encontrado."
}
```

### 6. Busca livros por categoria `GET /livro/buscar/categoria`

- **Query Params**
   - Key: categoria
   - Value: FANTASIA

Exemplo de URL: `http://localhost:8080/livro/buscar/categoria?categoria=FANTASIA`

#### Responses
```
(200 OK)
{
   "id": 1,
   "titulo": "O Senhor dos Anéis",
   "autor": "J.R.R. Tolkien",
   "anoPublicacao": 1954,
   "categoria": "FANTASIA"
}

(404 NOT FOUND)
{
    "error": "404",
    "message": "Livro não encontrado."
}
```

### 7. Deleta um livro `GET /livro/deletar/{id}`

Exemplo de URL: `http://localhost:8080/livro/deletar/1`

#### Responses
```
(204 NO CONTENT)
1

(404 NOT FOUND)
{
    "error": "404",
    "message": "Livro não encontrado."
}
```