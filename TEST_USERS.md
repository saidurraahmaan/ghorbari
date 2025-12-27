# Test Users for Development

When you run the application, Liquibase will automatically seed these test users.

## Seeded Users

### 1. Super Admin (Platform Owner)
- **Username:** `superadmin`
- **Email:** `superadmin@ghorbari.com`
- **Password:** `111111`
- **Role:** ROLE_SUPER_ADMIN
- **Tenant:** Sunrise Towers (ID: 1)
- **Access:** Platform-wide access (can access all tenants)

### 2. Tenant Admin (Sunrise Towers Owner)
- **Username:** `admin_sunrise`
- **Email:** `admin@sunrise.com`
- **Password:** `222222`
- **Role:** ROLE_TENANT_ADMIN
- **Tenant:** Sunrise Towers (tenant_key: `sunrise`)
- **Access:** Full access to Sunrise Towers only

### 3. Manager (Sunrise Towers Property Manager)
- **Username:** `manager_sunrise`
- **Email:** `manager@sunrise.com`
- **Password:** `333333`
- **Role:** ROLE_MANAGER
- **Tenant:** Sunrise Towers (tenant_key: `sunrise`)
- **Access:** Day-to-day operations in Sunrise Towers

### 4. Resident (Sunrise Towers Resident)
- **Username:** `john_resident`
- **Email:** `john@sunrise.com`
- **Password:** `444444`
- **Role:** ROLE_RESIDENT
- **Tenant:** Sunrise Towers (tenant_key: `sunrise`)
- **Access:** Self-service features only

## How to Test

### Test Login (with curl)

**Super Admin:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -H "X-Tenant-Key: sunrise" \
  -d '{
    "email": "superadmin@ghorbari.com",
    "password": "111111"
  }'
```

**Tenant Admin:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -H "X-Tenant-Key: sunrise" \
  -d '{
    "email": "admin@sunrise.com",
    "password": "222222"
  }'
```

**Manager:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -H "X-Tenant-Key: sunrise" \
  -d '{
    "email": "manager@sunrise.com",
    "password": "333333"
  }'
```

**Resident:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -H "X-Tenant-Key: sunrise" \
  -d '{
    "email": "john@sunrise.com",
    "password": "444444"
  }'
```

### Test Access Control

**Create Building (Should work for Tenant Admin, fail for Manager/Resident):**
```bash
# Login as Tenant Admin first to get token
TOKEN="<jwt_token_from_login>"

# Try to create building
curl -X POST http://localhost:8080/api/buildings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Building A",
    "address": "123 Main St",
    "floors": 10,
    "status": "ACTIVE"
  }'
```

**Expected Results:**
- ✅ **Tenant Admin** (222222): SUCCESS (has ROLE_TENANT_ADMIN)
- ❌ **Manager** (333333): FAIL - Access Denied (needs ROLE_TENANT_ADMIN)
- ❌ **Resident** (444444): FAIL - Access Denied (needs ROLE_TENANT_ADMIN)

**Update Building (Should work for Tenant Admin & Manager):**
```bash
curl -X PUT http://localhost:8080/api/buildings/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Building A Updated",
    "address": "123 Main St",
    "floors": 12,
    "status": "ACTIVE"
  }'
```

**Expected Results:**
- ✅ **Tenant Admin** (222222): SUCCESS
- ✅ **Manager** (333333): SUCCESS (has ROLE_MANAGER)
- ❌ **Resident** (444444): FAIL - Access Denied

**Create Booking (Should work for all except staff):**
```bash
curl -X POST http://localhost:8080/api/amenity-bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amenityId": 1,
    "residentId": 1,
    "startTime": "2025-12-30T10:00:00",
    "endTime": "2025-12-30T12:00:00"
  }'
```

**Expected Results:**
- ✅ **Tenant Admin** (222222): SUCCESS
- ✅ **Manager** (333333): SUCCESS
- ✅ **Resident** (444444): SUCCESS (ROLE_RESIDENT can create bookings)

## Frontend Login Testing

### For React/Next.js/Vue:

```typescript
// Login as Tenant Admin
const loginAsTenantAdmin = async () => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-Tenant-Key': 'sunrise'
    },
    body: JSON.stringify({
      email: 'admin@sunrise.com',
      password: '222222'
    })
  });

  const data = await response.json();
  localStorage.setItem('token', data.token);
  console.log('Logged in as Tenant Admin:', data);
};

// Login as Manager
const loginAsManager = async () => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-Tenant-Key': 'sunrise'
    },
    body: JSON.stringify({
      email: 'manager@sunrise.com',
      password: '333333'
    })
  });

  const data = await response.json();
  localStorage.setItem('token', data.token);
  console.log('Logged in as Manager:', data);
};

// Login as Resident
const loginAsResident = async () => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-Tenant-Key': 'sunrise'
    },
    body: JSON.stringify({
      email: 'john@sunrise.com',
      password: '444444'
    })
  });

  const data = await response.json();
  localStorage.setItem('token', data.token);
  console.log('Logged in as Resident:', data);
};
```

## Security Notes

⚠️ **IMPORTANT:** These are development/testing credentials only!

- All passwords are simple 6-digit numbers for easy testing
- In production, enforce strong password policies
- Change these passwords before deploying to production
- Consider adding password reset functionality

## Database Verification

To verify users were created:

```sql
-- View all users with their roles
SELECT
    u.id,
    u.username,
    u.email,
    u.tenant_id,
    r.name as role
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
WHERE u.email IN (
    'superadmin@ghorbari.com',
    'admin@sunrise.com',
    'manager@sunrise.com',
    'john@sunrise.com'
);
```

Expected output:
```
id | username         | email                    | tenant_id | role
---+------------------+--------------------------+-----------+------------------
 1 | superadmin       | superadmin@ghorbari.com  |         1 | ROLE_SUPER_ADMIN
 2 | admin_sunrise    | admin@sunrise.com        |         1 | ROLE_TENANT_ADMIN
 3 | manager_sunrise  | manager@sunrise.com      |         1 | ROLE_MANAGER
 4 | john_resident    | john@sunrise.com         |         1 | ROLE_RESIDENT
```
