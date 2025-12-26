# Tournament Planner

A Spring Boot application for managing tournaments, teams, and locations.

## Project Structure

This is a multi-module Maven project:

```
tournament-planner/
├── pom.xml                          # Parent POM with dependency management
├── tournament-planner-backend/      # REST API backend module
├── container-setup/                 # Database container configuration
├── http/                            # HTTP client test files
└── .devcontainer/                   # VS Code dev container setup
```

## Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Java 24 |
| Framework | Spring Boot 3.5.x |
| Database | PostgreSQL 16+ |
| Build Tool | Maven |
| ORM | Spring Data JPA / Hibernate |
| API Docs | OpenAPI / Swagger UI |
| Testing | JUnit 5, TestContainers |
| Metrics | Micrometer / Prometheus |

## Prerequisites

- **Java 24** (or compatible JDK)
- **Maven 3.9+**
- **Docker** or **Podman** (for database and integration tests)

## Quick Start

### 1. Start the Database

Using Podman:
```bash
cd container-setup
podman-compose up -d
```

Using Docker:
```bash
cd container-setup
docker-compose -f podman-compose.yml up -d
```

This starts a PostgreSQL instance on `localhost:5432` with:
- Database: `tournament-planner`
- Username: `root`
- Password: `root`

### 2. Build the Project

```bash
mvn clean install
```

To skip tests during build:
```bash
mvn clean install -DskipTests
```

### 3. Run the Application

```bash
cd tournament-planner-backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

### 4. Access Swagger UI

Open `http://localhost:8080/api/swagger-ui.html` in your browser to explore the API documentation.

## Development

### Building with Test Coverage

```bash
mvn clean install -Pcoverage
```

Coverage reports are generated in `tournament-planner-backend/target/site/jacoco/`.

### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=TournamentControllerTest

# Integration tests only
mvn test -Dtest=*IntegrationTest
```

### Using the Dev Container

This project includes a VS Code dev container configuration. To use it:

1. Install the "Remote - Containers" extension in VS Code
2. Open the project folder
3. Click "Reopen in Container" when prompted

The dev container includes Java 21, Maven, and Maven Daemon (mvnd) for faster builds.

## API Overview

Base URL: `/api`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tournaments/` | List all tournaments |
| GET | `/tournaments/{name}` | Search tournaments by name |
| POST | `/tournaments/` | Create a new tournament |
| PUT | `/tournaments/` | Update a tournament |
| DELETE | `/tournaments/delete/{id}` | Delete a tournament |

See the [Backend README](tournament-planner-backend/README.md) for detailed API documentation.

## Project Modules

### [tournament-planner-backend](tournament-planner-backend/README.md)

The main REST API module containing:
- Tournament, Team, and Location entities
- REST controllers and services
- Database repositories
- Exception handling

## CI/CD

This project uses GitHub Actions for continuous integration:

- **Build & Test**: Runs on every push and pull request
- **Code Coverage**: JaCoCo reports with badge generation
- **Code Quality**: Qodana static analysis

## Configuration

### Application Properties

Key configuration in `tournament-planner-backend/src/main/resources/application.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| `server.servlet.context-path` | `/api` | API base path |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/tournament-planner` | Database URL |
| `spring.datasource.username` | `root` | Database username |
| `spring.datasource.password` | `root` | Database password |

### Logging

Logs are written to:
- Console (during development)
- `$HOME/local/logs/tournament-planner/` (rolling file appender)

## Database Schema

```
┌─────────────┐       ┌─────────────────────┐       ┌─────────────┐
│  Tournament │       │ TournamentLocation  │       │  Location   │
├─────────────┤       ├─────────────────────┤       ├─────────────┤
│ id (PK)     │──────<│ tournament_id (FK)  │>──────│ id (PK)     │
│ name        │       │ location_id (FK)    │       │ city        │
│ start_date  │       └─────────────────────┘       │ venue       │
│ end_date    │                                     └─────────────┘
└─────────────┘

┌─────────────┐
│    Team     │
├─────────────┤
│ id (PK)     │
│ name        │
│ coach       │
└─────────────┘
```

## Contributing

1. Create a feature branch from `develop`
2. Make your changes
3. Ensure tests pass: `mvn test`
4. Submit a pull request to `develop`

## License

This project is proprietary software.
