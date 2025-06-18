# 📋 CollabTask API

Este projeto consiste em uma **API RESTful** desenvolvida em **Java 17** com o framework **Spring Boot**, seguindo os princípios da **Arquitetura Hexagonal** (Ports and Adapters). O principal objetivo do sistema é fazer o gerenciamento de tarefas, promovendo uma estrutura modular, testável e de fácil manutenção.

---

## 📌 Visão Geral

- **Linguagem e Framework:** Java 17 com Spring Boot  
- **Arquitetura:** Hexagonal (Ports and Adapters)  
- **Banco de Dados:** H2 (em memória)  
    
        Justificativa para uso do H2:
          
        O banco de dados relacional H2 foi escolhido por ser leve, embutido e de fácil configuração. Ele atende bem às necessidades de desenvolvimento local e testes automatizados, sem depender de serviços externos.
- **Documentação:** Swagger / OpenAPI  / OpenAPIGenerator
- **Testes Automatizados:** Cobertura mínima de 60% da base de código da API  
- **Tratamento de Erros:** Implementação de handlers globais para exceções esperadas e inesperadas  
- **Logs:** Uso do Spring Boot Actuator e SLF4J para monitoramento e análise

### 🎯 Objetivo

> Desenvolver uma API organizada, escalável e desacoplada, aplicando práticas modernas de arquitetura de software, testes automatizados e documentação, de forma a garantir qualidade e manutenção sustentável do sistema.

---

## 🧠 Decisões Arquiteturais

O projeto adota a **Arquitetura Hexagonal** com o intuito de separar claramente as responsabilidades do sistema em camadas independentes:

# 📁 Estrutura de Diretórios

```
collabtaskapi/
logs/
main/
├── java/
│   └── com/example/collabtaskapi/
│       ├── adapters/
│       │   ├── inbound/
│       │   │   ├── rest/
│       │   │   └── security/
│       │   └── outbound/
│       │       ├── persistence/
│       │       │   ├── entities/
│       │       │   └── repositories/
│       │       └── security/
│       ├── application/
│       │   ├── ports/
│       │   │   ├── inbound/
│       │   │   └── outbound/
│       │   └── usecases/
│       ├── domain/
│       │   └── enums/
│       ├── infrastructure/
│       │   ├── config/
│       │   └── exceptions/
│       └── utils/
│           └── mappers/
└── resources/
    ├── data/
    └── swagger/
        ├── paths/
        └── schemas/
```

- **Domain:** núcleo da aplicação, contendo os objetos java puro.  
- **Application:** Junto com o *Domain*, forma o core da aplicação, pois dentro dos *usecases*, tem as regras da aplicação e os *ports* com as interfaces que o core expõe.  
- **Adapters:** implementação concreta das portas, como persistência de dados, serviços REST, mapeamentos e integrações externas.  
- **Infrastructure:** Configuração do ambiente, tal como segurança e exceções.

Essa abordagem facilita **testes automatizados**, **flexibilidade para trocar tecnologias** (ex: banco de dados ou APIs externas) e **isolamento do domínio** em relação a detalhes de infraestrutura.

---
# 🚀 Endpoints

### Usuários

- `POST /account` → Criar um novo usuário
- `GET /account/{id}` → Obter informações de um usuário específico
- `PUT /account/{id}` → Atualizar informações do usuário
- `DELETE /account/{id}` → Remover um usuário (soft delete)
- `GET /account` → Obter informações de todas as contas

### Tarefas

- `POST /task` → Criar uma nova tarefa
- `GET /task/{id}` → Obter detalhes de uma tarefa
- `PUT /task/{id}` → Atualizar uma tarefa
- `DELETE /task/{id}` → Remover uma tarefa
- `GET /task/all` → Listar todas as tarefas
- `GET /task/assignedTo` → Listar as tarefas de acordo com o filtro

### Auth

- `POST /auth/login` → Solicitar acesso à API
- `POST /auth/logout` → Logout da API
---

## 🔐 Segurança e Controle de Acesso

A aplicação utiliza **JWT (JSON Web Tokens)** com Spring Security. O token JWT deve ser enviado no cabeçalho `Authorization` com o prefixo `Bearer`.

### 🛡️ Papéis de Usuário

| Papel    | Descrição                                                                 |
|----------|---------------------------------------------------------------------------|
| `ADMIN`  | Acesso completo à API, incluindo gerenciamento de contas e tarefas alheias. |
| `USER`   | Pode criar, remover, atualizar e visualizar suas próprias tarefas.                 |
| `VIEW`   | Acesso somente leitura (GET) aos dados públicos.                          |

### Autenticação e Autorização

O token JWT utilizado inclui a claim `"role"` que determina o papel do usuário, por exemplo:

```json
{
  "iss": "collab",
  "sub": "Alice",
  "role": "ADMIN",
  "exp": 1750269923,
  "iat": 1750266323
}
```

### 🔒 Restrições de Endpoints

| Método | Endpoint                    | Papéis Autorizados              |
|--------|-----------------------------|---------------------------------|
| `GET`  | `/task/**`                  | `ADMIN`, `USER`, `VIEW`         |
| `GET`  | `/account/**`               | `ADMIN`, `USER`, `VIEW`         |
| `POST` | `/task`                     | `ADMIN`, `USER`                 |
| `POST` | `/account`                  | `ADMIN`                         |
| `PUT`  | `/**`                       | `ADMIN`, `USER`                 |
| `DELETE` | `/task/**`                | `ADMIN`, `USER`                 |
| `DELETE` | `/account/**`             | `ADMIN`                         |
| `POST` | `/auth/**`                  | Público                         |
| `GET`  | `/swagger-ui/**`, `/v3/**`, `/h2-console/**` | Público           |

> ℹ️ Os papel `ADMIN` é o único que possui permissão para mexer diretamente nas contas.

---

# 🔧 Como executar o projeto

Siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/collabtask-api.git
   cd collabtask-api
   ```

2. **Execute a aplicação com Maven:**
   ```bash
   ./mvnw generate-sources
   ./mvnw spring-boot:run
   ```
   2.1 **Ou se preferir, utilizar o docker**
    ``` bash  
        docker-compose down -v
        docker-compose up --build
    ```

3. **Acesse a API e a documentação Swagger:**
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

> 💡 Para testes locais com H2, o console está disponível em: `http://localhost:8080/h2-console`  
> O JDBC URL geralmente é: `jdbc:h2:mem:testdb`

---

### ✅ Testando com curl

```bash
curl -X GET http://localhost:8080/task \
  -H "Authorization: Bearer <seu_token_jwt>"
```

---
# 🧪 Executando os Testes

Para executar os testes automatizados do projeto, siga os passos abaixo:

1. Abra o terminal e navegue até a pasta raiz do projeto (onde está o arquivo `pom.xml`):

2. Execute o comando:
    ```
    mvn test
    ```