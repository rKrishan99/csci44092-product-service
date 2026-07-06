# Product Service

Owns product data for the CSCI 44092 e-commerce microservices project. Exposes REST APIs to create, retrieve, and delete products, backed by PostgreSQL.

Part of a three-service system: **Product Service** (this repo) · [Order Service](https://github.com/rKrishan99/csci44092-order-service) · [Notification Service](https://github.com/rKrishan99/csci44092-notification-service).

## Stack

- Java 17, Spring Boot 3.5.15
- Spring Data JPA + Hibernate
- PostgreSQL (runtime), H2 (tests)
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito

## Running locally

Requires PostgreSQL reachable at `localhost:5432` — the fastest way is via the shared `docker-compose.yml` at the root of the three-repo workspace, which also creates the `product_db` database automatically:

```bash
docker compose up -d postgres
```

Then start the service:

```bash
./mvnw spring-boot:run
```

The app starts on **port 8081**.

## Configuration

All connection details are environment-variable driven (see `src/main/resources/application.properties`) — nothing is hardcoded:

| Variable | Default | Purpose |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/product_db` | JDBC connection string |
| `DB_USERNAME` | `postgres` | Database user |
| `DB_PASSWORD` | `postgres` | Database password |

## API

Interactive docs: `http://localhost:8081/swagger-ui/index.html`

| Method | Path | Description |
|---|---|---|
| `POST` | `/products` | Create a product |
| `GET` | `/products` | List all products |
| `GET` | `/products/{id}` | Get a product by ID |
| `DELETE` | `/products/{id}` | Delete a product by ID |

**Product entity:** `productId`, `name`, `unitPrice`, `description`, `category`, `stock`.

**Example — create a product:**
```bash
curl -X POST http://localhost:8081/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Sony WH-1000XM4","unitPrice":349.99,"description":"Wireless Noise Cancelling Headphones","category":"Electronics","stock":120}'
```

## Tests

```bash
./mvnw test
```

Covers the controller and service layers (`ProductControllerTest`, `ProductServiceTest`).

## Docker

```bash
docker build -t product-service:local .
docker run -p 8081:8081 \
  -e DB_URL="jdbc:postgresql://<host>:5432/product_db" \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  product-service:local
```

Public image: [`docker.io/rkrishan/product-service`](https://hub.docker.com/r/rkrishan/product-service)

## Performance testing

A JMeter load test plan for `GET /products/{id}` (20 threads, 10s ramp-up, 10 loops) is at [`tests/ProductService_LoadTest.jmx`](tests/ProductService_LoadTest.jmx).

## Cloud deployment

Deployed to Azure — PostgreSQL Flexible Server + Azure Container Apps running this same Docker image, configured entirely via the environment variables above (no code or image changes between local, Docker, and cloud).
