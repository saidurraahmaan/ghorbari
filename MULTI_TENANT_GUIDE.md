# Multi-Tenant Architecture Guide

This project implements a **shared schema multi-tenancy** approach for an apartment management SaaS application.

## Architecture Overview

### Components

1. **BaseEntity** - Common fields for all entities (id, timestamps)
2. **TenantAwareEntity** - Adds tenant isolation via `tenant_id` column
3. **Tenant** - Stores tenant metadata (apartment complexes)
4. **TenantContext** - Thread-local storage for current tenant ID
5. **TenantInterceptor** - Extracts tenant from HTTP requests
6. **Hibernate Filter** - Automatic query filtering by tenant

## How It Works

### 1. Tenant Identification

The system extracts tenant ID from HTTP requests using:
- **Header**: `X-Tenant-ID: 1`
- **Subdomain**: `tenant1.yourdomain.com` (requires mapping)
- **JWT Token**: (implement when adding authentication)

### 2. Automatic Tenant Filtering

Every query automatically filters by tenant:

```java
@Entity
public class Apartment extends TenantAwareEntity {
    private String apartmentNumber;
    // tenant_id is automatically added by TenantAwareEntity
}

// When you query:
List<Apartment> apartments = apartmentRepository.findAll();
// SQL: SELECT * FROM apartments WHERE tenant_id = 1
```

### 3. Automatic Tenant Assignment

When creating entities, tenant_id is automatically set:

```java
Apartment apt = new Apartment();
apt.setApartmentNumber("101");
apartmentRepository.save(apt);
// tenant_id is automatically set from TenantContext
```

## Usage Examples

### Creating a New Tenant

```java
Tenant tenant = new Tenant("greenwood-apartments", "Greenwood Apartments");
tenant.setDescription("Luxury apartment complex in downtown");
tenantRepository.save(tenant);
```

### Making API Calls

All API calls to `/api/**` require the `X-Tenant-ID` header:

```bash
# Create an apartment for tenant 1
curl -X POST http://localhost:8081/api/apartments \
  -H "Content-Type: application/json" \
  -H "X-Tenant-ID: 1" \
  -d '{
    "apartmentNumber": "101",
    "floor": 1,
    "bedrooms": 2,
    "bathrooms": 1,
    "rentAmount": 1500.00,
    "status": "VACANT"
  }'

# Get all apartments for tenant 1
curl http://localhost:8081/api/apartments \
  -H "X-Tenant-ID: 1"
```

### Creating Tenant-Aware Entities

```java
@Entity
@Table(name = "payments")
public class Payment extends TenantAwareEntity {
    private BigDecimal amount;
    private LocalDate paymentDate;

    @ManyToOne
    private Resident resident;

    // No need to add tenant_id - it's in TenantAwareEntity
}
```

### Creating Global Entities (Not Tenant-Specific)

```java
@Entity
@Table(name = "system_config")
public class SystemConfig extends BaseEntity {
    // This extends BaseEntity, NOT TenantAwareEntity
    // So it won't have tenant_id and won't be filtered
    private String configKey;
    private String configValue;
}
```

## Database Schema

The system will create these tables:

```sql
-- Global table (no tenant_id)
CREATE TABLE tenants (
    id BIGSERIAL PRIMARY KEY,
    tenant_key VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- Tenant-scoped tables (with tenant_id)
CREATE TABLE apartments (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    apartment_number VARCHAR(50) NOT NULL,
    building VARCHAR(100),
    floor INTEGER NOT NULL,
    bedrooms INTEGER NOT NULL,
    bathrooms INTEGER NOT NULL,
    square_footage DECIMAL(10,2),
    rent_amount DECIMAL(10,2),
    status VARCHAR(20) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

CREATE TABLE residents (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    apartment_id BIGINT,
    move_in_date DATE,
    move_out_date DATE,
    status VARCHAR(20) NOT NULL,
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenants(id),
    FOREIGN KEY (apartment_id) REFERENCES apartments(id)
);
```

## Security Considerations

1. **Always validate tenant access** - The interceptor sets tenant context, but you should validate user permissions
2. **Use unique constraints carefully** - Email uniqueness should be per-tenant or global
3. **Audit logging** - Track which tenant performed which actions
4. **Database indexes** - Add indexes on `tenant_id` for better performance

```sql
CREATE INDEX idx_apartments_tenant_id ON apartments(tenant_id);
CREATE INDEX idx_residents_tenant_id ON residents(tenant_id);
```

## Testing Multi-Tenancy

```java
@Test
void testTenantIsolation() {
    // Set tenant 1
    TenantContext.setCurrentTenantId(1L);

    Apartment apt1 = new Apartment("101", 1, 2, 1);
    apartmentRepository.save(apt1);

    // Switch to tenant 2
    TenantContext.setCurrentTenantId(2L);

    Apartment apt2 = new Apartment("101", 1, 2, 1);
    apartmentRepository.save(apt2);

    // Verify tenant 2 can't see tenant 1's data
    List<Apartment> apartments = apartmentRepository.findAll();
    assertEquals(1, apartments.size());
    assertEquals(2L, apartments.get(0).getTenantId());

    TenantContext.clear();
}
```

## Next Steps

1. **Add Authentication** - JWT tokens with tenant claim
2. **Tenant Registration API** - Allow self-service signup
3. **Subdomain Routing** - Map subdomains to tenant IDs
4. **Tenant Admin Panel** - Manage tenant settings
5. **Billing Integration** - Track usage per tenant
6. **Data Export** - Allow tenants to export their data
7. **Custom Branding** - Per-tenant themes and logos

## Common Pitfalls

1. **Forgetting to clear context** - Always use try-finally or the interceptor handles it
2. **Native queries** - Add `WHERE tenant_id = :tenantId` manually
3. **Bulk operations** - Ensure they respect tenant filter
4. **Background jobs** - Set tenant context manually in async tasks

```java
@Async
public void processPayments(Long tenantId) {
    try {
        TenantContext.setCurrentTenantId(tenantId);
        // Process payments
    } finally {
        TenantContext.clear();
    }
}
```
