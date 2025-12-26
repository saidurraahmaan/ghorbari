# Developer Setup Guide

Welcome to the Ghorbari project! Follow these steps to set up your local development environment.

## Prerequisites

- Java 25
- Maven 3.6+
- PostgreSQL 12+
- Git

## Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ghorbari
```

### 2. Set Up PostgreSQL Database

Create a local PostgreSQL database:

```bash
createdb ghor_bari
# Or use your preferred database name
```

### 3. Configure Liquibase (Database Migrations)

```bash
cd core/src/main/resources/db/changelog
cp liquibase.properties.example liquibase.properties
```

Edit `liquibase.properties`:
```properties
url=jdbc:postgresql://localhost:5432/ghor_bari
username=your_postgres_user
password=your_postgres_password
author=Your Name
```

📖 **Detailed guide**: [core/LIQUIBASE_SETUP.md](core/LIQUIBASE_SETUP.md)

### 4. Configure Spring Boot Application

```bash
cd web/src/main/resources
cp application-dev.yaml.example application-dev.yaml
```

Edit `application-dev.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ghor_bari
    username: your_postgres_user
    password: your_postgres_password

jwt:
  secret: <generate-your-secret>  # Use: openssl rand -base64 64
```

**IMPORTANT**: Make sure database settings match between `liquibase.properties` and `application-dev.yaml`!

📖 **Detailed guide**: [web/APPLICATION_CONFIG_SETUP.md](web/APPLICATION_CONFIG_SETUP.md)

### 5. Apply Database Migrations

```bash
mvn liquibase:update -pl core
```

This creates all required database tables.

### 6. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run -pl web
```

The application will be available at: http://localhost:8081

Swagger API Documentation: http://localhost:8081/swagger-ui.html

## Project Structure

```
ghorbari/
├── core/                           # Domain layer (entities, repositories, services)
│   ├── src/main/resources/db/changelog/
│   │   ├── liquibase.properties    # Your local DB config (git-ignored)
│   │   └── liquibase.properties.example
│   └── LIQUIBASE_SETUP.md         # Liquibase guide
│
├── web/                            # Presentation layer (controllers, security)
│   ├── src/main/resources/
│   │   ├── application-dev.yaml    # Your local config (git-ignored)
│   │   ├── application-dev.yaml.example
│   │   ├── application.yaml        # Base config
│   │   ├── application-prod.yaml   # Production config
│   │   └── application-staging.yaml
│   └── APPLICATION_CONFIG_SETUP.md # Spring Boot config guide
│
└── pom.xml                         # Parent POM
```

## Development Workflow

### Making Entity Changes

1. Modify your JPA entities
2. Generate changelog:
   ```bash
   mvn liquibase:diff -pl core
   ```
3. Apply changes:
   ```bash
   mvn liquibase:update -pl core
   ```
4. Commit the generated changelog file

### Pulling Changes from Others

When you pull changes that include new database migrations:

```bash
git pull
mvn liquibase:update -pl core
```

Liquibase automatically applies only new migrations.

### Running Tests

```bash
mvn test
```

## Configuration Files (What to Commit)

### ✅ Commit These
- `liquibase.properties.example`
- `application-dev.yaml.example`
- `application.yaml`
- `application-prod.yaml`
- `application-staging.yaml`
- Generated changelogs in `db/changelog/changes/`

### ❌ Don't Commit These (Git-Ignored)
- `liquibase.properties` - Your local database credentials
- `application-dev.yaml` - Your local application config

## Troubleshooting

### Database Connection Issues

**Error**: "Connection refused" or "Could not connect to database"

**Solution**:
1. Check PostgreSQL is running
2. Verify credentials in both `liquibase.properties` and `application-dev.yaml`
3. Ensure database exists: `psql -l | grep ghor_bari`

### Liquibase Errors

**Error**: "Could not find liquibase.properties"

**Solution**:
```bash
cd core/src/main/resources/db/changelog
cp liquibase.properties.example liquibase.properties
# Edit with your database settings
```

### Application Won't Start

**Error**: "Could not find application-dev.yaml"

**Solution**:
```bash
cd web/src/main/resources
cp application-dev.yaml.example application-dev.yaml
# Edit with your settings
```

### Port Already in Use

**Error**: "Port 8081 is already in use"

**Solution**:
Update `application-dev.yaml`:
```yaml
server:
  port: 8082  # Or any available port
```

## API Testing

### Using Swagger UI

1. Start the application
2. Navigate to http://localhost:8081/swagger-ui.html
3. Test endpoints interactively

### Using cURL

```bash
# Health check
curl http://localhost:8081/api/health

# Register user
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'

# Login
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

## Multi-Tenancy

This application uses shared schema multi-tenancy. See [MULTI_TENANT_GUIDE.md](MULTI_TENANT_GUIDE.md) for details.

All API requests (except auth) require the `X-Tenant-ID` header:

```bash
curl http://localhost:8081/api/apartments \
  -H "X-Tenant-ID: 1" \
  -H "Authorization: Bearer <your-jwt-token>"
```

## Need Help?

- [Liquibase Setup Guide](core/LIQUIBASE_SETUP.md)
- [Application Config Setup](web/APPLICATION_CONFIG_SETUP.md)
- [Multi-Tenant Guide](MULTI_TENANT_GUIDE.md)
- Project documentation: Check the `/docs` folder
- Ask the team on Slack/Discord

## Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Make your changes
3. Generate Liquibase changelogs if you modified entities
4. Commit your changes (including generated changelogs)
5. Push and create a Pull Request

**Important**:
- Never commit `liquibase.properties` or `application-dev.yaml`
- Always commit generated changelog files from `db/changelog/changes/`
- Test locally before pushing

Happy coding! 🚀
