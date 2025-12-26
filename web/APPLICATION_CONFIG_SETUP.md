# Spring Boot Application Configuration Setup

## First Time Setup

When you clone this repository, you need to create your local development configuration:

### 1. Copy the Template File

```bash
cd web/src/main/resources
cp application-dev.yaml.example application-dev.yaml
```

### 2. Update with Your Local Settings

Edit `application-dev.yaml` and update these values:

#### Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:YOUR_PORT/YOUR_DATABASE_NAME
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
```

#### JWT Secret
Generate a new JWT secret for local development:
```bash
# Generate a random secret
echo -n "my-super-secret-key-for-local-dev" | base64

# Or use openssl
openssl rand -base64 64
```

Then update:
```yaml
jwt:
  secret: YOUR_GENERATED_SECRET_HERE
```

#### Server URL (if different)
```yaml
app:
  swagger:
    server:
      url: http://localhost:YOUR_PORT
```

### 3. That's It!

The `application-dev.yaml` file is git-ignored, so your local settings won't be committed.

## Configuration Files Overview

### Developer-Specific (Git-Ignored)
- ❌ `application-dev.yaml` - Your local development config (git-ignored)
  - Database credentials
  - JWT secrets
  - Local ports and URLs

### Committed Configuration Files
- ✅ `application.yaml` - Base configuration (defaults, common settings)
- ✅ `application-dev.yaml.example` - Template for developers
- ✅ `application-prod.yaml` - Production environment config
- ✅ `application-staging.yaml` - Staging environment config

## Running the Application

### Development Mode (uses application-dev.yaml)
```bash
# Default profile is 'dev' (set in application.yaml)
mvn spring-boot:run

# Or explicitly
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Staging Mode
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=staging
```

### Production Mode
```bash
# Build first
mvn clean package

# Run with prod profile
java -jar target/ghorbari-web-*.jar --spring.profiles.active=prod
```

## Important Notes

### Database Configuration
Your local `application-dev.yaml` database settings should match your local PostgreSQL:
- Same database name as in `liquibase.properties`
- Same username and password
- Same port

**Example matching configuration:**

`liquibase.properties`:
```properties
url=jdbc:postgresql://localhost:5432/ghor_bari
username=postgres
password=mypassword
```

`application-dev.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ghor_bari
    username: postgres
    password: mypassword
```

### JWT Secret Security
- **Development**: Use any secret (can share in team)
- **Production**: Use strong, unique secret (environment variable recommended)
- Never commit real production secrets to git

### Environment Variables (Alternative Approach)
Instead of `application-dev.yaml`, you can use environment variables:

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/ghor_bari
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=mypassword
export JWT_SECRET=your-secret-here
mvn spring-boot:run
```

## Troubleshooting

### "Could not find application-dev.yaml"
```bash
cd web/src/main/resources
cp application-dev.yaml.example application-dev.yaml
# Edit application-dev.yaml with your settings
```

### "Connection refused" or Database Errors
Check that:
1. PostgreSQL is running
2. Database name, username, password are correct in `application-dev.yaml`
3. Port matches your local PostgreSQL instance

### "Invalid JWT signature" Errors
Your JWT secret might be different from what was used to generate tokens.
- Clear browser cookies/local storage
- Login again to get new tokens

### Application Uses Wrong Profile
Check `application.yaml` for active profile:
```yaml
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}
```

Override with:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Configuration Priority

Spring Boot loads configuration in this order (later overrides earlier):

1. `application.yaml` - Base configuration
2. `application-{profile}.yaml` - Profile-specific config
3. Environment variables - `DATABASE_URL`, `JWT_SECRET`, etc.
4. Command-line arguments - `--server.port=8080`

## Best Practices

1. **Never commit `application-dev.yaml`** - Contains credentials
2. **Keep example file updated** - When adding new config, update `.example`
3. **Use environment variables in prod** - Don't hardcode production secrets
4. **Document new configuration** - Add comments explaining what settings do
5. **Match database configs** - Ensure `liquibase.properties` and `application-dev.yaml` use same DB

## For CI/CD

In CI/CD pipelines, use environment variables instead of config files:

```yaml
# GitHub Actions example
env:
  DATABASE_URL: ${{ secrets.DATABASE_URL }}
  DATABASE_USERNAME: ${{ secrets.DATABASE_USERNAME }}
  DATABASE_PASSWORD: ${{ secrets.DATABASE_PASSWORD }}
  JWT_SECRET: ${{ secrets.JWT_SECRET }}
```

## Need Help?

- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [Spring Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
