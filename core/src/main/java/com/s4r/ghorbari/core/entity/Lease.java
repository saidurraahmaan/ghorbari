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

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "monthly_rent", precision = 10, scale = 2, nullable = false)
    private BigDecimal monthlyRent;

    @Column(name = "security_deposit", precision = 10, scale = 2)
    private BigDecimal securityDeposit;

    @Column(name = "payment_due_day")
    private Integer paymentDueDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaseStatus status = LeaseStatus.DRAFT;

    @Column(name = "terms_conditions", length = 5000)
    private String termsConditions;

    @Column(name = "special_clauses", length = 2000)
    private String specialClauses;

    @Column(name = "signed_date")
    private LocalDate signedDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "termination_reason", length = 1000)
    private String terminationReason;

    @Column(length = 1000)
    private String notes;

    public enum LeaseStatus {
        DRAFT,
        ACTIVE,
        EXPIRING_SOON,
        EXPIRED,
        TERMINATED,
        RENEWED
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public Integer getPaymentDueDay() {
        return paymentDueDay;
    }

    public void setPaymentDueDay(Integer paymentDueDay) {
        this.paymentDueDay = paymentDueDay;
    }

    public LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(LeaseStatus status) {
        this.status = status;
    }

    public String getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    public String getSpecialClauses() {
        return specialClauses;
    }

    public void setSpecialClauses(String specialClauses) {
        this.specialClauses = specialClauses;
    }

    public LocalDate getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDate signedDate) {
        this.signedDate = signedDate;
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
