# Projeto

API Rest de planetas do universo Starwars

## Iniciando

O projeto foi desenvolvido no Eclipse IDE como um projeto gerenciado pelo Maven.
 
Pode ser importado como um projeto Maven existente na IDE.

Ex.: File > Import > Existing Maven Project 

### Pré-requisitos

- [Eclipse IDE](http://help.eclipse.org/oxygen/index.jsp)
- [Maven](https://maven.apache.org/)
- [Java 8](https://www.java.com/pt_BR/)

### Instalação

* **mvn install**

Para instalação do projeto basta executar o comando acima através do Eclipse ou CLI
	

## Testes

Os testes de integração são executados automaticamente ao executar o passo de instalação ou através do JUNIT contido no eclipse. 

## Deploy

* **mvn clean spring-boot:run**

Para deploy do projeto basta executar o comando acima através do Eclipse ou CLI:

Este comando vai iniciar a API no seguinte endereço: [http://localhost:9090/starwars/api](http://localhost:9090/starwars/api)

A documentação da API pode ser acessada através da seguinte interface: [http://localhost:9090/starwars/api/swagger-ui.html](http://localhost:9090/starwars/api/swagger-ui.html)  

## Arquitetura

* [SpringBoot](https://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/)
* [Maven](https://maven.apache.org/) - Injeção de Dependências
* [Swagger2](https://swagger.io/docs/) - Documentação da API
* [MongoDB](https://docs.mongodb.com) - Base de Dados NoSQL

## Authors

* **Filipe Oliveira** - *Development API* - [https://github.com/FilipeSoares](https://github.com/FilipeSoares)
