# Authentication Service

A robust, production-ready authentication microservice built with Spring Boot, JWT, and PostgreSQL. This service provides secure user registration, login, and role-based access control for your microservices architecture.

---

## Features

- **JWT Authentication:** Secure stateless authentication using JSON Web Tokens.
- **Role-Based Access Control:** Supports `USER`, `MODERATOR`, and `ADMIN` roles.
- **User Registration & Login:** REST endpoints for signup and login.
- **Password Encryption:** Secure password storage with BCrypt.
- **Integration Ready:** Easily connect with other microservices via REST.
- **Dockerized:** Ready for containerized deployment with Docker and Docker Compose.
- **Integration Testing:** Includes support for integration tests with Testcontainers.

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

### Running Locally

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-org/authentication-service.git
   cd authentication-service
   ```

2. **Build and start with Docker Compose:**
   ```sh
   ./docker-manager.sh up
   ```
   Or for development:
   ```sh
   ./docker-manager.sh develop
   ```

3. **API will be available at:**  
   `http://localhost:8000/api/auth`

---

## API Endpoints

### Authentication

- `POST /api/auth/register` — Register a new user
- `POST /api/auth/login` — Login and receive JWT

### Test Endpoints

- `GET /api/auth/test/all` — Public access
- `GET /api/auth/test/user` — Requires USER, MODERATOR, or ADMIN role
- `GET /api/auth/test/mod` — Requires MODERATOR role
- `GET /api/auth/test/admin` — Requires ADMIN role

---

## Configuration

Configuration is managed via application.yml:

```yaml
auth:
  app:
    jwtSecret: <your-512-bit-base64-secret>
    jwtExpirationMs: 86400000
spring:
  datasource:
    url: jdbc:postgresql://authentication-db:5432/authentication_db
    username: auth_db_user
    password: <your-db-password>
```

---

## Database

- Uses PostgreSQL.
- Roles are seeded automatically on startup.
- User and role relationships managed via JPA.

---

## Running Tests

```sh
./mvnw test
```

---

## Useful Commands

| Command                                      | Description                                      |
|-----------------------------------------------|--------------------------------------------------|
| `.docker-manager.sh up`                      | Start all services in detached mode              |
| `.docker-manager.sh develop`                 | Start all services in foreground (dev mode)      |
| `.docker-manager.sh down`                    | Stop and remove all containers                   |
| `.docker-manager.sh build`                   | Build all Docker images                          |
| `.docker-manager.sh rebuild`                 | Rebuild all images (no cache) and restart        |
| `.docker-manager.sh rebuild-service SERVICE` | Rebuild and restart a specific service           |
| `.docker-manager.sh reset`                   | Remove all containers, images, and volumes, then rebuild and start everything |
| `.docker-manager.sh prune`                   | Remove all unused Docker resources               |
| `.docker-manager.sh logs [SERVICE]`          | View logs for all or a specific service          |
| `.docker-manager.sh exec SERVICE`            | Open a shell in a running service container      |

**Examples:**  
```sh
./docker-manager.sh logs authentication-service
./docker-manager.sh exec authentication-service
```

---

## Database Access

To enter the PostgreSQL shell for the authentication database, run:

```sh
docker exec -it authentication-service_authentication-db_1 psql -U auth_db_user -d authentication_db
```

Then you can run SQL queries, for example:

```sql
SELECT * FROM roles;
```
