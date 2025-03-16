# Reservaki - Sistema de Reservas para Restaurantes

Sistema de Reserva e Avaliação de Restaurantes.

A Spring Boot está com deploy na nuvem em 2 end-points:
No Render e na AWS EC2 e compartilham o mesmo banco de dados que esta no Render.


## Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.1** - Framework para desenvolvimento de aplicações
    - Spring Web - Para APIs REST
    - Spring Data JPA - Para persistência de dados
    - Spring Validation - Para validação de dados

### Banco de Dados
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Gerenciamento de migrações de banco de dados

### Documentação
- **SpringDoc OpenAPI 2.3.0** - Para documentação da API com Swagger UI

### Testes
- **JUnit 5** - Framework de testes
- **Spring Boot Test** - Suporte para testes com Spring
- **Testcontainers 1.19.3** - Containers Docker para testes de integração
- **JaCoCo 0.8.10** - Análise de cobertura de código
- **Gatling 3.7.6** - Testes de carga e performance

### Qualidade de Código
- **Checkstyle 3.3.1** - Verificação de estilo de código
- **Lombok** - Redução de código boilerplate

### CI/CD
- **GitHub Actions** - Integração e entrega contínua
- **Docker & Docker Compose** - Containerização da aplicação

### Ferramentas de Build
- **Maven** - Gerenciamento de dependências e build

## Comandos Úteis

#### - Compilar o projeto e executar todos os testes
```bash
mvn clean package
```

#### - Executar apenas os testes unitários
```bash
mvn test -Dtest=com.reservaki.reservaki.application.**.*Test
```

#### - Executar apenas os testes de integração
```bash
mvn test -Dtest=com.reservaki.reservaki.integration.**.*Test
```

#### - Executar todos os testes
```bash
mvn test
```

#### - Gerar relatório de cobertura com JaCoCo
```bash
mvn test jacoco:report
```
#### Visualizar o relatório de cobertura:
##### Windows:
```bash 
start target/site/jacoco/index.html
```
##### macOS:
```bash 
open target/site/jacoco/index.html
```
##### Linux:
```bash 
xdg-open target/site/jacoco/index.html
```

## Iniciar a aplicação (Docker)
##### Iniciar a aplicação e o banco de dados
```bash 
docker-compose up -d app db
```
#####  Verificar logs da aplicação
```bash 
docker-compose logs -f app
```
#####  Executar testes de carga com Gatling
```bash 
docker-compose up gatling
```
Os resultados dos testes de carga estarão disponíveis em gatling-docker/results/.


### Documentação da API

A documentação da API está disponível através do Swagger UI após iniciar a aplicação:

http://localhost:8080/swagger-ui.html


### CI/CD Pipeline

##### O projeto utiliza GitHub Actions para automação de testes e build. O pipeline executa:

1. Compilação do código
2. Execução de todos os testes
3. Geração de relatórios
4. Criação do pacote de distribuição

### URL do DEPLOY

**Render** - https://reservaki.onrender.com/swagger-ui/index.html#/
**AWS EC2** - http://18.212.31.228:8080/swagger-ui/index.html
