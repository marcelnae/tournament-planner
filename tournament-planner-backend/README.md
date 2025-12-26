# Tournament Planner Backend

REST API backend for the Tournament Planner application.

## Module Overview

This module provides a RESTful API for managing tournaments, built with Spring Boot and PostgreSQL.

## Project Structure

```
src/
├── main/
│   ├── java/com/marcel/tournament/backend/
│   │   ├── TournamentBackendApplication.java    # Application entry point
│   │   ├── GlobalExceptionHandler.java          # Centralized error handling
│   │   ├── tournament/                          # Tournament domain
│   │   │   ├── Tournament.java                  # JPA entity
│   │   │   ├── TournamentDTO.java               # Data transfer object
│   │   │   ├── TournamentController.java        # REST controller
│   │   │   ├── TournamentService.java           # Business logic
│   │   │   ├── ITournamentService.java          # Service interface
│   │   │   ├── TournamentRepository.java        # Data access
│   │   │   ├── TournamentException.java         # Custom exception
│   │   │   └── TournamentLocation.java          # Join entity
│   │   ├── team/                                # Team domain
│   │   │   ├── Team.java                        # JPA entity
│   │   │   └── TeamDTO.java                     # Data transfer object
│   │   └── location/                            # Location domain
│   │       ├── Location.java                    # JPA entity
│   │       └── LocationDTO.java                 # Data transfer object
│   └── resources/
│       ├── application.properties               # Application configuration
│       └── logback-spring.xml                   # Logging configuration
└── test/
    ├── java/com/marcel/tournament/backend/tournament/
    │   ├── TournamentControllerTest.java        # Unit tests
    │   ├── TournamentControllerIntegrationTest.java  # Integration tests
    │   └── PostgresTestContainerConfig.java     # TestContainers setup
    └── resources/
        ├── test-tournaments-data.sql            # Test data
        └── testcontainers.properties            # Container configuration
```

## Architecture

The module follows a layered architecture:

```
┌─────────────────────────────────────────────────────────────┐
│                     REST Controllers                        │
│              (TournamentController)                         │
├─────────────────────────────────────────────────────────────┤
│                     Service Layer                           │
│     (ITournamentService / TournamentService)                │
├─────────────────────────────────────────────────────────────┤
│                   Repository Layer                          │
│              (TournamentRepository)                         │
├─────────────────────────────────────────────────────────────┤
│                      Database                               │
│                    (PostgreSQL)                             │
└─────────────────────────────────────────────────────────────┘
```

### Key Design Patterns

- **DTO Pattern**: Entities are mapped to DTOs for API responses
- **Repository Pattern**: JPA repositories for data access
- **Service Layer**: Business logic separated from controllers
- **Global Exception Handling**: Centralized error responses via `@ControllerAdvice`

## Entities

### Tournament

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key (auto-generated) |
| name | String | Tournament name (max 100 chars) |
| startDate | ZonedDateTime | Tournament start date |
| endDate | ZonedDateTime | Tournament end date (optional) |

### Team

| Field | Type | Description |
|-------|------|-------------|
| id | Integer | Primary key (auto-generated) |
| name | String | Team name (max 100 chars) |
| coach | String | Coach name (optional, max 100 chars) |

### Location

| Field | Type | Description |
|-------|------|-------------|
| id | Integer | Primary key (auto-generated) |
| city | String | City name (max 100 chars) |
| venue | String | Venue name (max 100 chars) |

## API Endpoints

Base path: `/api/tournaments`

### Get All Tournaments

```http
GET /api/tournaments/
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "EURO 2024",
    "startDate": "2024-06-14T18:00:00+02:00",
    "endDate": "2024-07-14T21:00:00+02:00"
  }
]
```

### Search Tournaments by Name

```http
GET /api/tournaments/{name}
```

Performs a substring search (case-sensitive).

**Example:**
```http
GET /api/tournaments/EURO
```

### Create Tournament

```http
POST /api/tournaments/
Content-Type: application/json

{
  "name": "Champions Cup",
  "startDate": "2025-07-01T10:00:00+00:00",
  "endDate": "2025-07-10T18:00:00+00:00"
}
```

**Response:** Returns the created tournament with generated ID.

### Update Tournament

```http
PUT /api/tournaments/
Content-Type: application/json

{
  "id": 1,
  "name": "Champions Cup - Final Edition"
}
```

**Note:** Currently only the name can be updated.

### Delete Tournament

```http
DELETE /api/tournaments/delete/{id}
```

**Example:**
```http
DELETE /api/tournaments/delete/1
```

## Error Handling

All errors return a consistent JSON structure:

```json
{
  "timestamp": "2025-01-15T10:30:00.000+00:00",
  "error": "TournamentException",
  "message": "Tournament with id 999 not found"
}
```

### HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 400 | Bad request (validation error) |
| 404 | Resource not found |
| 500 | Internal server error |

## Configuration

### application.properties

```properties
spring.application.name=tournament-backend
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/tournament-planner
spring.datasource.username=root
spring.datasource.password=root
```

### Environment Variables

You can override configuration using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://myhost:5432/mydb
export SPRING_DATASOURCE_USERNAME=myuser
export SPRING_DATASOURCE_PASSWORD=mypassword
```

## Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using the JAR

```bash
mvn package -DskipTests
java -jar target/tournament-planner-backend.jar
```

## Logging

Logs are written to:
- **Console**: Standard output during development
- **File**: `$HOME/local/logs/tournament-planner/`

Log files use daily rolling with 30-day retention.

Find logs in devcontainer in `/root/local/logs`

## Testing

### Test Structure

- **Unit Tests** (`TournamentControllerTest`): Test controllers with mocked services using MockMvc
- **Integration Tests** (`TournamentControllerIntegrationTest`): Full stack tests with real database using TestContainers

### Running Tests

```bash
# All tests
mvn test

# Unit tests only
mvn test -Dtest=TournamentControllerTest

# Integration tests only
mvn test -Dtest=TournamentControllerIntegrationTest
```

## Running Integration Tests

Integration tests use [Testcontainers](https://testcontainers.com/) to spin up a PostgreSQL database. By default, Testcontainers expects Docker, but you can also use Podman.

### Using Docker

No additional configuration needed. Just run:

```bash
mvn test
```

### Using Podman

To use Podman instead of Docker, you need to:

1. **Enable the Podman socket**

   ```bash
   systemctl --user enable podman.socket
   systemctl --user start podman.socket
   ```

2. **Create `~/.testcontainers.properties`** with the following content:

   ```properties
   docker.host=unix:///run/user/<your-uid>/podman/podman.sock
   ```

   Replace `<your-uid>` with your user ID (run `id -u` to find it). For example:

   ```properties
   docker.host=unix:///run/user/1000/podman/podman.sock
   ```

3. **Run the tests**

   ```bash
   mvn test
   ```

#### Troubleshooting

- **Socket not found**: Ensure the Podman socket is running:
  ```bash
  systemctl --user status podman.socket
  ```

- **Permission denied**: Check socket permissions:
  ```bash
  ls -la /run/user/$(id -u)/podman/podman.sock
  ```

- **Alternative: Environment variable**: Instead of the properties file, you can set:
  ```bash
  export DOCKER_HOST=unix:///run/user/$(id -u)/podman/podman.sock
  ```

## API Documentation

When the application is running, access the interactive API documentation:

- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8080/api/v3/api-docs`

## HTTP Client Files

The `/http` directory contains HTTP client files for IntelliJ IDEA:

- `smoketests-tournaments.http`: Example API requests
- `http-client.env.json`: Environment configuration

To use them in IntelliJ:
1. Open the `.http` file
2. Click the green play button next to any request
3. Select the environment (if applicable)
