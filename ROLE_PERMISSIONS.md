# Role-Based Access Control

## How to Use

Add `@RequiresRole` annotation to your service methods:

```java
import com.s4r.ghorbari.core.security.RequiresRole;
import com.s4r.ghorbari.core.entity.Role.RoleName;

@Service
public class BuildingService {

    // Only TENANT_ADMIN can create buildings
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void createBuilding(BuildingDto dto) {
        // ...
    }

    // TENANT_ADMIN or MANAGER can update
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateBuilding(Long id, BuildingDto dto) {
        // ...
    }

    // Anyone authenticated can view
    public List<BuildingDto> getAllBuildings() {
        // ...
    }
}
```

## Role Hierarchy

### ROLE_SUPER_ADMIN (Platform Owner)
- Access to ALL tenants
- Can create/delete tenants
- Platform-wide administration
- **Use Case**: You (the SaaS owner)

### ROLE_TENANT_ADMIN (Apartment Complex Owner)
- **Full access** within their tenant
- Can manage:
  - Buildings (CREATE, UPDATE, DELETE)
  - Apartments (CREATE, UPDATE, DELETE)
  - All Residents (VIEW, CREATE, UPDATE, DELETE)
  - All Leases (VIEW, CREATE, UPDATE, DELETE)
  - All Invoices (VIEW, CREATE, UPDATE, DELETE)
  - All Payments (VIEW, CREATE, UPDATE, DELETE)
  - Amenities (CREATE, UPDATE, DELETE)
  - All Bookings (VIEW, APPROVE, REJECT)
  - Announcements (CREATE, UPDATE, DELETE)
  - Users (CREATE, ASSIGN ROLES)
  - Tenant Settings (UPDATE)
  - Reports (VIEW ALL)

### ROLE_MANAGER (Property Manager)
- **Day-to-day operations**
- Can manage:
  - Buildings (VIEW, UPDATE)
  - Apartments (VIEW, UPDATE)
  - Residents (VIEW, CREATE, UPDATE)
  - Leases (VIEW, CREATE, UPDATE)
  - Invoices (VIEW, CREATE, UPDATE)
  - Payments (VIEW, CREATE)
  - Amenities (VIEW, UPDATE)
  - Bookings (VIEW, APPROVE, REJECT)
  - Announcements (CREATE, VIEW, UPDATE)
  - Reports (VIEW OPERATIONAL)
- **Cannot**:
  - Delete buildings/apartments
  - Delete residents or leases
  - Manage users/roles
  - Change tenant settings

### ROLE_STAFF (Maintenance/Front Desk)
- **Limited operational access**
- Can:
  - View residents
  - View bookings
  - Create/view announcements
  - View amenities
- **Cannot**:
  - Access financial data
  - Manage leases
  - Approve bookings
  - Modify buildings/apartments

### ROLE_RESIDENT (Tenant/Resident)
- **Self-service only**
- Can:
  - View own lease
  - View own invoices
  - View own payments
  - Book amenities
  - View announcements
- **Cannot**:
  - Access other residents' data
  - View financial reports
  - Manage anything

## Recommended Permissions

### Building Management
```java
@RequiresRole(RoleName.ROLE_TENANT_ADMIN)
void createBuilding(BuildingDto dto);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void updateBuilding(Long id, BuildingDto dto);

@RequiresRole(RoleName.ROLE_TENANT_ADMIN)
void deleteBuilding(Long id);

// Anyone can view
List<BuildingDto> getAllBuildings();
```

### Apartment Management
```java
@RequiresRole(RoleName.ROLE_TENANT_ADMIN)
void createApartment(ApartmentDto dto);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void updateApartment(Long id, ApartmentDto dto);

@RequiresRole(RoleName.ROLE_TENANT_ADMIN)
void deleteApartment(Long id);
```

### Resident Management
```java
@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void createResident(ResidentDto dto);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void updateResident(Long id, ResidentDto dto);

@RequiresRole(RoleName.ROLE_TENANT_ADMIN)
void deleteResident(Long id);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_STAFF})
List<ResidentDto> getAllResidents();
```

### Lease Management
```java
@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void createLease(LeaseDto dto);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void updateLease(Long id, LeaseDto dto);

@RequiresRole(RoleName.ROLE_TENANT_ADMIN)
void deleteLease(Long id);
```

### Invoice & Payment Management
```java
@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void createInvoice(InvoiceDto dto);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void createPayment(PaymentDto dto);

// Residents can view their own (add custom logic in service)
@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_RESIDENT})
List<InvoiceDto> getInvoices();
```

### Amenity Booking
```java
@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_RESIDENT})
void createBooking(AmenityBookingDto dto);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void approveBooking(Long id);

@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
void rejectBooking(Long id);
```

### Announcements
```java
@RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_STAFF})
void createAnnouncement(AnnouncementDto dto);

// Everyone can view
List<AnnouncementDto> getAllAnnouncements();
```

## How It Works

1. **Annotation** (`@RequiresRole`) is placed on service method
2. **Aspect** (`AccessControlAspect`) intercepts the method call
3. **Checks** if current user has required role
4. **Allows** or **denies** access (throws `ServiceException` with `ACCESS_DENIED`)

## Error Handling

When access is denied, a `ServiceException` is thrown:

```java
throw new ServiceException(ErrorCode.ACCESS_DENIED, "Access denied: Insufficient permissions");
```

This is caught by your global exception handler and returns:

```json
{
  "code": 7002,
  "message": "Access denied: Insufficient permissions",
  "timestamp": "2025-12-28T00:00:00"
}
```

## Example Usage

### BuildingService.java
```java
@Service
@Transactional
public class BuildingService implements IBuildingService {

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void createBuilding(BuildingDto dto) {
        // Only tenant admins can create buildings
        // ...
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateBuilding(Long id, BuildingDto dto) {
        // Tenant admins and managers can update
        // ...
    }

    @Override
    public List<BuildingDto> getAllBuildings() {
        // No annotation = anyone authenticated can view
        // ...
    }
}
```

## Testing

To test, create users with different roles and verify access:

```bash
# Create tenant admin
curl -X POST http://localhost:8080/api/auth/register \
  -H "X-Tenant-Key: sunrise" \
  -d '{"username":"admin","email":"admin@sunrise.com","password":"pass123"}'

# Manually assign ROLE_TENANT_ADMIN in database

# Login and get JWT
curl -X POST http://localhost:8080/api/auth/login \
  -H "X-Tenant-Key: sunrise" \
  -d '{"email":"admin@sunrise.com","password":"pass123"}'

# Try to create building (should work)
curl -X POST http://localhost:8080/api/buildings \
  -H "Authorization: Bearer <token>" \
  -d '{"name":"Building A","address":"123 Main St"}'
```
