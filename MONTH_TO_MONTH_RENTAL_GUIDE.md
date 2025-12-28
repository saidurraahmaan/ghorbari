# Month-to-Month Rental System Guide

## Overview

The Ghorbari system now supports **both fixed-term and month-to-month leases**, allowing for flexible rental arrangements where tenants can leave with a notice period (default: 2 months).

## Lease Types

### 1. MONTH_TO_MONTH (Default)
- **No fixed end date** - The lease continues indefinitely until notice is given
- **Notice period** - Tenant must give notice before leaving (default: 60 days / 2 months)
- **Monthly billing** - Invoices are generated monthly
- **Flexible** - Tenant can leave anytime after giving proper notice

### 2. FIXED_TERM
- **Specific end date** - Lease has a defined start and end date (e.g., 1 year)
- **Traditional lease** - Common for long-term commitments
- **Renewal required** - Must be renewed after end date

## Key Fields

### Lease Entity Fields

| Field | Type | Description | Required |
|-------|------|-------------|----------|
| `leaseType` | Enum | MONTH_TO_MONTH or FIXED_TERM | Yes |
| `startDate` | LocalDate | When the lease begins | Yes |
| `endDate` | LocalDate | End date (only for FIXED_TERM) | No* |
| `noticePeriodMonths` | Integer | Notice period in months (default: 2) | No |
| `advancePaymentMonths` | Integer | Advance payment months (1-2 months) | No |
| `noticeGivenDate` | LocalDate | When tenant gave notice to leave | No |
| `monthlyRent` | BigDecimal | Monthly rental amount | Yes |
| `securityDeposit` | BigDecimal | Security deposit amount | No |

*Required for FIXED_TERM, nullable for MONTH_TO_MONTH

## How It Works

### Creating a Month-to-Month Lease

```java
LeaseDto lease = new LeaseDto();
lease.setLeaseType(Lease.LeaseType.MONTH_TO_MONTH);
lease.setStartDate(LocalDate.now());
lease.setEndDate(null); // No end date!
lease.setNoticePeriodMonths(2); // 2 months notice
lease.setAdvancePaymentMonths(1); // 1 month advance payment (optional)
lease.setMonthlyRent(new BigDecimal("1800.00"));
lease.setSecurityDeposit(new BigDecimal("3600.00"));
lease.setStatus(Lease.LeaseStatus.ACTIVE);
```

### Tenant Gives Notice to Leave

```java
// Tenant decides to leave and gives notice on January 1st
leaseService.giveNotice(leaseId, LocalDate.of(2025, 1, 1));

// System automatically:
// 1. Sets noticeGivenDate = January 1, 2025
// 2. Changes status to TERMINATED
// 3. Calculates expected move-out date = March 1, 2025 (2 months later)
```

### Calculating Expected Move-Out Date

```java
LocalDate moveOutDate = leaseService.calculateExpectedMoveOutDate(leaseId);
// Returns: noticeGivenDate + noticePeriodMonths
// Example: January 1 + 2 months = March 1
```

## Monthly Invoice Generation

The system automatically generates monthly invoices for **ACTIVE** month-to-month leases:

```java
// InvoiceService.generateMonthlyInvoices() runs monthly
// - Generates invoice for all ACTIVE leases
// - Continues until lease status changes to TERMINATED
// - After notice is given, continues invoicing until actual move-out
```

### Invoice Timeline Example

```
Month 1 (Jan): Lease ACTIVE, Invoice generated ✓
Month 2 (Feb): Lease ACTIVE, Invoice generated ✓
Feb 15: Tenant gives notice (2 months)
Month 3 (Mar): Status = TERMINATED, Invoice still generated ✓
Month 4 (Apr): Status = TERMINATED, Invoice still generated ✓
Apr 15: Expected move-out (2 months from Feb 15)
Apr 15: Tenant moves out, stop generating invoices ✗
```

## Lease Status Flow

### Month-to-Month Lease

```
DRAFT → ACTIVE → TERMINATED
         ↑          ↓
         └──────(notice given)
```

### Fixed-Term Lease

```
DRAFT → ACTIVE → EXPIRED (when end_date passes)
         ↓
         └──→ TERMINATED (early termination)
```

## API Usage Examples

### 1. Create Month-to-Month Lease

```http
POST /api/leases
{
  "apartmentId": 1,
  "primaryResidentId": 5,
  "leaseType": "MONTH_TO_MONTH",
  "startDate": "2025-01-01",
  "endDate": null,
  "noticePeriodMonths": 2,
  "advancePaymentMonths": 1,
  "monthlyRent": 1800.00,
  "securityDeposit": 3600.00,
  "status": "ACTIVE"
}
```

### 2. Create Fixed-Term Lease (1 Year)

```http
POST /api/leases
{
  "apartmentId": 2,
  "primaryResidentId": 8,
  "leaseType": "FIXED_TERM",
  "startDate": "2025-01-01",
  "endDate": "2026-01-01",
  "monthlyRent": 2200.00,
  "status": "ACTIVE"
}
```

### 3. Give Notice (Month-to-Month Only)

```http
POST /api/leases/{leaseId}/give-notice
{
  "noticeDate": "2025-02-15"
}
```

Response:
```json
{
  "success": true,
  "expectedMoveOutDate": "2025-04-15",
  "remainingMonths": 2
}
```

### 4. Calculate Expected Move-Out Date

```http
GET /api/leases/{leaseId}/expected-move-out-date
```

Response:
```json
{
  "leaseId": 123,
  "leaseType": "MONTH_TO_MONTH",
  "noticeGivenDate": "2025-02-15",
  "noticePeriodMonths": 2,
  "expectedMoveOutDate": "2025-04-15"
}
```

## Business Rules

### Month-to-Month Leases

1. ✅ **No end date required** - Can leave `endDate` as `null`
2. ✅ **Default notice period** - 2 months if not specified
3. ✅ **Flexible notice period** - Can customize per lease (1, 2, 3 months, etc.)
4. ✅ **Advance payment** - Optional 1-2 months advance payment
5. ✅ **Continuous billing** - Invoices generated monthly until termination
6. ✅ **Notice tracking** - System tracks when notice was given
7. ✅ **Move-out calculation** - Automatically calculates expected move-out date

### Fixed-Term Leases

1. ✅ **End date required** - Must specify when lease ends
2. ✅ **Renewal process** - Can be renewed by updating end date (status remains ACTIVE)
3. ✅ **Expiry tracking** - Status changes to EXPIRED when end_date passes
4. ❌ **No notice period** - Uses end date instead

## Database Schema

```sql
CREATE TABLE leases (
    id BIGSERIAL PRIMARY KEY,
    apartment_id BIGINT,
    primary_resident_id BIGINT,
    lease_type VARCHAR(20) NOT NULL DEFAULT 'MONTH_TO_MONTH',
    start_date DATE NOT NULL,
    end_date DATE,                       -- Nullable for MONTH_TO_MONTH, required for FIXED_TERM
    notice_period_months INT DEFAULT 2,  -- For MONTH_TO_MONTH leases
    advance_payment_months INT,          -- For MONTH_TO_MONTH: 1-2 months advance
    notice_given_date DATE,
    monthly_rent DECIMAL(10,2) NOT NULL,
    security_deposit DECIMAL(10,2),
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',  -- DRAFT, ACTIVE, TERMINATED, EXPIRED
    termination_date DATE,
    termination_reason VARCHAR(1000),
    notes VARCHAR(1000),
    -- ... audit fields (created_at, updated_at, tenant_id, etc.)
);
```

## Best Practices

### 1. Default to Month-to-Month
For maximum flexibility, use `MONTH_TO_MONTH` as the default lease type:

```java
lease.setLeaseType(Lease.LeaseType.MONTH_TO_MONTH);
```

### 2. Standard Notice Period
Use 2 months as the standard notice period:

```java
lease.setNoticePeriodMonths(2);
```

### 3. Track Notice Immediately
When tenant gives notice, record it immediately:

```java
leaseService.giveNotice(leaseId, LocalDate.now());
```

### 4. Calculate Move-Out in Advance
Calculate and display expected move-out date when notice is given:

```java
LocalDate moveOutDate = leaseService.calculateExpectedMoveOutDate(leaseId);
// Show to tenant: "Expected move-out: March 1, 2025"
```

### 5. Continue Invoicing Until Actual Move-Out
Generate invoices for the notice period:

```java
// Even if status = TERMINATED, continue invoicing until move-out date
if (lease.getStatus() == TERMINATED && today.isBefore(expectedMoveOutDate)) {
    generateInvoice(lease);
}
```

## Migration from Fixed-Term to Month-to-Month

If you have existing fixed-term leases and want to convert them:

```java
Lease lease = leaseRepository.findById(id).get();
lease.setLeaseType(Lease.LeaseType.MONTH_TO_MONTH);
lease.setEndDate(null);
lease.setNoticePeriodMonths(2);
lease.setAdvancePaymentMonths(1); // Optional
leaseRepository.save(lease);
```

## Summary

✅ **Month-to-Month is now the default** - Perfect for your use case
✅ **2-month notice period** - Configurable per lease
✅ **No bond/long-term commitment** - Flexible for both tenant and landlord
✅ **Automatic billing** - Invoices generated monthly
✅ **Notice tracking** - System knows when tenant plans to leave
✅ **Backward compatible** - Still supports fixed-term leases if needed

The system now fully supports your business model where tenants rent monthly and can leave with 2 months notice!
