# StarWars API

API RESTful de planetas do universo Starwars

## Overview

API RESTful do universo starwars.

Apenas o recurso de planetas é exposto e permite realizar funções como:

- Listar os planetas cadastrados;
- Consultar um planeta pelo seu identificador;
- Buscar planetas pelo seu nome;
- Excluir um planeta;

Informações como número de aparições em filmes também são possíveis de verificar na Consulta a um determinado planeta, esta informação é recuperada através de integração com a API [swapi.co/api](https://swapi.co/api).

A base de dados utilizada para a persistência de informações de planetas é o MongoDB carregado em memória no momento da execução da API.

A implementação de paginação na lista de resultados não foi realizada ainda nesta versão, porém em versão futura estará disponível.

## Arquitetura

- [Java 8.x.x](https://www.java.com/pt_BR/)
- [Maven 3.3.0](https://maven.apache.org/)
- [SpringBoot 2.1.4](https://spring.io/projects/spring-boot)
- [MongoDB 3.8.2](https://www.mongodb.com/)
- [Swagger 2.8.0](https://swagger.io/docs/)

## Instalação

```console
mvn install
```

## Testes

```console
mvn verify
```

## Running

```console
mvn spring-boot:run
```

## URL's

- API  => [http://localhost:9000/starwars/api/v1](http://localhost:9000/starwars/api/v1)

- Docs => [http://localhost:9000/starwars/api/v1/swagger-ui.html](http://localhost:9000/starwars/api/v1/swagger-ui.html)

- Actuator => [http://localhost:9000/starwars/api/v1/actuator](http://localhost:9000/starwars/api/v1/actuator)  

## Authors

* **Filipe Oliveira** - *Developer*  - [filipesomstd@gmail.com](mailto:filipesomstd@gmail.com)<br/>
[https://github.com/FilipeSoares](https://github.com/FilipeSoares)
<br/>[https://bitbucket.org/filipe_soares/](https://bitbucket.org/filipe_soares/)
