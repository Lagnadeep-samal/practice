# Microservices with Spring Boot 3 and Spring Cloud

A multi-module Maven project implementing the microservices architecture from
your course outline: Eureka Discovery Server + API Gateway + Account and Loan
microservices, ready to be paired with a React frontend.

## Modules

| Module            | Port | Purpose                                                            |
|--------------------|------|---------------------------------------------------------------------|
| `eureka-server`    | 8761 | Service registry — all other services register here                |
| `api-gateway`      | 8080 | Single entry point; routes `/api/accounts/**` and `/api/loans/**`   |
| `account-service`  | 8081 | CRUD for customer accounts (H2 in-memory DB)                        |
| `loan-service`     | 8082 | CRUD for customer loans (H2 in-memory DB)                           |

## Prerequisites

- Java 17+
- Maven 3.8+
- (Internet access to download dependencies from Maven Central the first time you build)

## Build

From the project root:

```bash
mvn clean install
```

## Run (start in this order, each in its own terminal)

```bash
# 1. Start the discovery server first
cd eureka-server
mvn spring-boot:run

# 2. Start the business microservices
cd account-service
mvn spring-boot:run

cd loan-service
mvn spring-boot:run

# 3. Start the gateway last (it needs Eureka to find the other services)
cd api-gateway
mvn spring-boot:run
```

## Verify it's working

- Eureka dashboard: http://localhost:8761 — you should see `ACCOUNT-SERVICE`,
  `LOAN-SERVICE`, and `API-GATEWAY` registered.
- H2 console: http://localhost:8081/h2-console (account-service) and
  http://localhost:8082/h2-console (loan-service) — JDBC URL is
  `jdbc:h2:mem:accountdb` / `jdbc:h2:mem:loandb`, user `sa`, empty password.

## API Endpoints (via the Gateway on port 8080)

### Account Service
| Method | Endpoint                    | Description        |
|--------|------------------------------|---------------------|
| POST   | /api/accounts                | Create an account   |
| GET    | /api/accounts                 | List all accounts   |
| GET    | /api/accounts/{accountId}     | Get one account     |
| PUT    | /api/accounts/{accountId}     | Update an account   |
| DELETE | /api/accounts/{accountId}     | Delete an account   |

Sample body:
```json
{
  "customerName": "John Doe",
  "accountType": "SAVINGS",
  "balance": 5000.0,
  "branchAddress": "123 Main St"
}
```

### Loan Service
| Method | Endpoint                | Description      |
|--------|--------------------------|-------------------|
| POST   | /api/loans                | Create a loan     |
| GET    | /api/loans                 | List all loans    |
| GET    | /api/loans/{loanId}        | Get one loan      |
| PUT    | /api/loans/{loanId}        | Update a loan     |
| DELETE | /api/loans/{loanId}        | Delete a loan     |

Sample body:
```json
{
  "customerName": "John Doe",
  "loanType": "HOME",
  "loanAmount": 250000.0,
  "outstandingAmount": 200000.0,
  "interestRate": 7.5
}
```

You can call these either directly on their own ports (8081/8082) or through
the gateway on port 8080 — the gateway forwards `/api/accounts/**` and
`/api/loans/**` to the right service using Eureka-based load balancing (`lb://`).

## Next steps (matching your outline)

- Add the ReactJS frontend and point it at `http://localhost:8080/api/...`
- Add a Spring Security / JWT-based authentication service in front of the
  gateway (the outline calls this out separately from the gateway itself)
- Containerize each service with Docker and add a `docker-compose.yml`
