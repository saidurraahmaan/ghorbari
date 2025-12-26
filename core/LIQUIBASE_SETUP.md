# Liquibase Setup Guide for Developers

## First Time Setup

When you clone this repository, you need to create your local Liquibase configuration:

### 1. Copy the Template File

```bash
cd core/src/main/resources/db/changelog
cp liquibase.properties.example liquibase.properties
```

### 2. Update with Your Local Database Settings

Edit `liquibase.properties` with your local database credentials:

```properties
url=jdbc:postgresql://localhost:5432/ghor_bari
username=your_postgres_user
password=your_postgres_password
author=Your Name
```

**That's all you need!** The file only contains:
- Database connection details (url, username, password)
- Author name for generated changelogs (optional, defaults to your system username)

All other Liquibase configuration (driver, changelog paths, Hibernate reference) is defined in `pom.xml`.

### 3. That's It!

The `liquibase.properties` file is git-ignored, so your local settings won't be committed.

## Common Commands

All commands should be run from the `core` module directory or use `-pl core` flag:

### Apply Database Changes
```bash
mvn liquibase:update -pl core
```

### Generate Changelog After Entity Changes
```bash
mvn liquibase:diff -pl core
```

### Generate and Apply in One Command
```bash
mvn liquibase:diff -pl core && mvn liquibase:update -pl core
```

### Check Status
```bash
mvn liquibase:status -pl core
```

## Development Workflow

### 1. Modify Your JPA Entities
Make changes to your entity classes (add/change fields, relationships, etc.)

### 2. Generate Diff Changelog
```bash
mvn liquibase:diff -pl core
```
This creates a timestamped file in `src/main/resources/db/changelog/changes/`
- Example: `20251227123456_changelog.xml`

### 3. Apply Changes to Database
```bash
mvn liquibase:update -pl core
```

### 4. Commit the Generated Changelog
```bash
git add core/src/main/resources/db/changelog/changes/20251227123456_changelog.xml
git commit -m "Add new column to User entity"
git push
```

The master changelog (`db.changelog-master.xml`) automatically includes all files from the `changes/` directory, so you don't need to edit it manually.

## For Other Developers

When you pull changes:

```bash
mvn liquibase:update -pl core
```

Liquibase automatically:
- Detects new changelog files
- Applies only the changes you haven't run yet
- Tracks execution in the `databasechangelog` table

## Important Files

- ✅ **Commit**: `liquibase.properties.example` (template for other developers)
- ✅ **Commit**: Generated changelogs in `db/changelog/changes/`
- ✅ **Commit**: `db.changelog-master.xml` (master changelog with `includeAll`)
- ❌ **Don't commit**: `liquibase.properties` (your local config, git-ignored)

## Project Structure

```
core/
└── src/main/resources/db/changelog/
    ├── db.changelog-master.xml          # Master changelog (includes all)
    ├── liquibase.properties.example     # Template (committed)
    ├── liquibase.properties             # Your local config (git-ignored)
    └── changes/                         # Auto-included by master
        ├── 20251226194600_changelog.xml # Initial schema
        ├── 20251227123456_changelog.xml # Added email column
        └── 20251228091200_changelog.xml # Added index
```

## How Auto-Include Works

The master changelog uses `includeAll`:

```xml
<includeAll path="db/changelog/changes/"
            relativeToChangelogFile="false"
            errorIfMissingOrEmpty="false"/>
```

This automatically:
- Includes all `.xml` files in `changes/` directory
- Executes them in alphabetical order (timestamps ensure correct order)
- No manual file inclusion needed

## Troubleshooting

### "Could not find liquibase.properties"
```bash
cd core/src/main/resources/db/changelog
cp liquibase.properties.example liquibase.properties
# Edit liquibase.properties with your database settings
```

### "Connection refused"
Check:
1. PostgreSQL is running
2. Port, database name, username, password are correct in `liquibase.properties`

### "No changes detected" after modifying entities
```bash
# Compile first to ensure entities are up to date
mvn clean compile -pl core
mvn liquibase:diff -pl core
```

### Changelogs already applied
Liquibase tracks executed changelogs in the `databasechangelog` table. If you need to re-run:
```sql
-- View what's been applied
SELECT * FROM databasechangelog ORDER BY dateexecuted;

-- Rollback last changeset (use with caution!)
mvn liquibase:rollback -Dliquibase.rollbackCount=1 -pl core
```

## Best Practices

1. **Never edit old changelogs** - Always create new ones
2. **Always run `diff` after entity changes** - Keep database in sync
3. **Commit changelogs with your entity changes** - Keep code and schema together
4. **Test locally before pushing** - Run `update` to ensure it works
5. **Use descriptive entity names** - They become table names in the changelog

## Configuration Details

### liquibase.properties (git-ignored, developer-specific)
Contains only your local settings:
- **url**: Database connection URL (e.g., `jdbc:postgresql://localhost:5432/ghor_bari`)
- **username**: Database username
- **password**: Database password
- **author**: Your name for changelog attribution (optional, defaults to system username)

### pom.xml (committed, project-wide)
Contains all other Liquibase configuration:
- **driver**: PostgreSQL JDBC driver
- **changeLogFile**: Master changelog location
- **outputChangeLogFile**: Output file for generateChangeLog
- **diffChangeLogFile**: Pattern for diff files (uses `${maven.build.timestamp}`)
- **referenceUrl**: Hibernate reference for entity comparison
  - Includes naming strategies to match Spring Boot defaults

This separation means:
- ✅ No merge conflicts on database credentials
- ✅ Each developer uses their own local database settings
- ✅ Project configuration stays consistent across the team

## Need Help?

- [Liquibase Documentation](https://docs.liquibase.com)
- [Liquibase Maven Plugin](https://docs.liquibase.com/tools-integrations/maven/home.html)
