package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "leases")
public class Lease extends TenantAwareEntity {

    @Column(name = "apartment_id")
    private Long apartmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", insertable = false, updatable = false)
    private Apartment apartment;

    @Column(name = "primary_resident_id")
    private Long primaryResidentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_resident_id", insertable = false, updatable = false)
    private Resident primaryResident;

    @Enumerated(EnumType.STRING)
    @Column(name = "lease_type", nullable = false)
    private LeaseType leaseType = LeaseType.MONTH_TO_MONTH;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate; // Required for FIXED_TERM, null for MONTH_TO_MONTH

    @Column(name = "notice_period_months")
    private Integer noticePeriodMonths = 2; // Default: 2 months notice for MONTH_TO_MONTH

    @Column(name = "notice_given_date")
    private LocalDate noticeGivenDate;

    @NotNull
    @Column(name = "monthly_rent", precision = 10, scale = 2, nullable = false)
    private BigDecimal monthlyRent;

    @Column(name = "security_deposit", precision = 10, scale = 2)
    private BigDecimal securityDeposit;

    @Column(name = "advance_payment_months")
    private Integer advancePaymentMonths; // For MONTH_TO_MONTH: 1 or 2 months advance

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaseStatus status = LeaseStatus.DRAFT;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "termination_reason", length = 1000)
    private String terminationReason;

    @Column(length = 1000)
    private String notes;

    public enum LeaseType {
        FIXED_TERM,      // Formal agreement for specific period (e.g., 1 year), can be renewed
        MONTH_TO_MONTH   // Family renter - informal, flexible, requires 1-2 months notice before leaving
    }

    public enum LeaseStatus {
        DRAFT,       // Lease being created but not yet active
        ACTIVE,      // Currently active lease
        TERMINATED,  // Ended early or notice given for MONTH_TO_MONTH
        EXPIRED      // Only for FIXED_TERM when end_date has passed
    }

    // Constructors
    public Lease() {
    }

    public Lease(Long apartmentId, Long primaryResidentId, LocalDate startDate, LocalDate endDate, BigDecimal monthlyRent) {
        this.apartmentId = apartmentId;
        this.primaryResidentId = primaryResidentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
    }

    // Getters and Setters
    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Long getPrimaryResidentId() {
        return primaryResidentId;
    }

    public void setPrimaryResidentId(Long primaryResidentId) {
        this.primaryResidentId = primaryResidentId;
    }

    public Resident getPrimaryResident() {
        return primaryResident;
    }

    public void setPrimaryResident(Resident primaryResident) {
        this.primaryResident = primaryResident;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LeaseType getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(LeaseType leaseType) {
        this.leaseType = leaseType;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getNoticePeriodMonths() {
        return noticePeriodMonths;
    }

    public void setNoticePeriodMonths(Integer noticePeriodMonths) {
        this.noticePeriodMonths = noticePeriodMonths;
    }

    public Integer getAdvancePaymentMonths() {
        return advancePaymentMonths;
    }

    public void setAdvancePaymentMonths(Integer advancePaymentMonths) {
        this.advancePaymentMonths = advancePaymentMonths;
    }

    public LocalDate getNoticeGivenDate() {
        return noticeGivenDate;
    }

    public void setNoticeGivenDate(LocalDate noticeGivenDate) {
        this.noticeGivenDate = noticeGivenDate;
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public BigDecimal getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(LeaseStatus status) {
        this.status = status;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
