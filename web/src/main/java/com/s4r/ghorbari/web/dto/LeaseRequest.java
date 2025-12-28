package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.entity.Lease;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseRequest {

    @NotNull(message = "Apartment ID is required")
    private Long apartmentId;

    @NotNull(message = "Primary resident ID is required")
    private Long primaryResidentId;

    @NotNull(message = "Lease type is required")
    private Lease.LeaseType leaseType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate; // Required for FIXED_TERM, null for MONTH_TO_MONTH

    private Integer noticePeriodMonths; // For MONTH_TO_MONTH leases

    private Integer advancePaymentMonths; // Number of months advance payment

    @NotNull(message = "Monthly rent is required")
    @Positive(message = "Monthly rent must be positive")
    private BigDecimal monthlyRent;

    @Positive(message = "Security deposit must be positive")
    private BigDecimal securityDeposit;

    private Lease.LeaseStatus status;

    private String notes;

    // Constructors
    public LeaseRequest() {
    }

    // Getters and Setters
    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Long getPrimaryResidentId() {
        return primaryResidentId;
    }

    public void setPrimaryResidentId(Long primaryResidentId) {
        this.primaryResidentId = primaryResidentId;
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

    public Lease.LeaseType getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(Lease.LeaseType leaseType) {
        this.leaseType = leaseType;
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

    public Lease.LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(Lease.LeaseStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
