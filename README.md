# Gerenciador de Disciplinas

Este é um projeto de API RESTful desenvolvido em Spring Boot para o gerenciamento de disciplinas, incluindo operações de criação, listagem, busca, atualização e exclusão (CRUD).

## Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.x
* **Persistência:** Spring Data JPA
* **Banco de Dados:** H2
* **Build Tool:** Maven
* **Documentação da API:** Swagger/OpenAPI

## Pré-requisitos

Antes de começar, você deve ter instalado em sua máquina:

* Java Development Kit (JDK) 17 ou superior
* Maven
* Git (para clonar o repositório)

## Configuração e Execução

### 1. Clonar o Repositório

```bash
git clone [https://github.com/jeanvitorvieira/gerenciador-disciplinas.git](https://github.com/jeanvitorvieira/gerenciador-disciplinas.git)
cd gerenciador-disciplinas
```

### 2. Executar a Aplicação
Você pode executar o projeto diretamente usando o Maven Wrapper:
```bash
# No Linux/macOS
./mvnw spring-boot:run

# No Windows
mvnw.cmd spring-boot:run
```
A aplicação será iniciada na porta 8080, conforme configurado em src/main/resources/application.properties.

## Endpoints da API e Documentação
O projeto utiliza o **H2 Database** em modo in-memory e possui a documentação da API via **Swagger/OpenAPI** ativada.

**1. Documentação Interativa (Swagger UI)**
Após iniciar a aplicação, acesse a documentação da API em seu navegador:
```bash
http://localhost:8080/swagger-ui.html
```

**2. Console do Banco de Dados H2**
Para visualizar o banco de dados em memória e as tabelas geradas pelo JPA, acesse o console H2:
```bash
http://localhost:8080/h2-console
```
**Credenciais de Conexão (Conforme application.properties):**

**JDBC URL:** jdbc:h2:mem:usuario

**User Name:** sa

**Password:**

**3. Principais Endpoints**

Método: **POST** | Endpoint: **/disciplinas** | Descrição: Cria uma nova disciplina.

Método: **GET** | Endpoint: **/disciplinas** | Descrição: Lista todas as disciplinas (com paginação e ordenação por nome).

Método: **GET** | Endpoint: **/disciplinas/{id}** | Descrição: Busca uma disciplina por ID.

Método: **PATCH** | Endpoint: **/disciplinas/{id}** | Descrição: Atualiza parcialmente uma disciplina.

Método: **DELETE** | Endpoint: **/disciplinas/{id}** | Descrição: Exclui uma disciplina por ID.

## Como rodar os testes
O projeto inclui testes de integração para a camada de serviço que garantem o funcionamento correto do CRUD e o tratamento das exceções (NotFoundException, ConflictException).

Para executar todos os testes configurados:
```bash
./mvnw clean test
```

## Tratamento de erros
A API utiliza um **GlobalExceptionHandler** para retornar códigos de status HTTP apropriados em caso de falha:

**404 Not Found:** Disparado ao tentar buscar, atualizar ou excluir um recurso com um ID inexistente.

**409 Conflict:** Disparado ao tentar criar ou atualizar uma disciplina com um nome que já existe (regra de negócio).
