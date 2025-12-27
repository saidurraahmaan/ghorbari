package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Lease;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseDto {

    private Long id;
    private Long apartmentId;
    private Long primaryResidentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal monthlyRent;
    private BigDecimal securityDeposit;
    private Integer paymentDueDay;
    private Lease.LeaseStatus status;
    private String termsConditions;
    private String specialClauses;
    private LocalDate signedDate;
    private LocalDate terminationDate;
    private String terminationReason;
    private String notes;

    // Constructors
    public LeaseDto() {
    }

    public LeaseDto(Lease lease) {
        this.id = lease.getId();
        this.apartmentId = lease.getApartmentId();
        this.primaryResidentId = lease.getPrimaryResidentId();
        this.startDate = lease.getStartDate();
        this.endDate = lease.getEndDate();
        this.monthlyRent = lease.getMonthlyRent();
        this.securityDeposit = lease.getSecurityDeposit();
        this.paymentDueDay = lease.getPaymentDueDay();
        this.status = lease.getStatus();
        this.termsConditions = lease.getTermsConditions();
        this.specialClauses = lease.getSpecialClauses();
        this.signedDate = lease.getSignedDate();
        this.terminationDate = lease.getTerminationDate();
        this.terminationReason = lease.getTerminationReason();
        this.notes = lease.getNotes();
    }

    public Lease toEntity() {
        Lease lease = new Lease();
        lease.setId(this.id);
        lease.setApartmentId(this.apartmentId);
        lease.setPrimaryResidentId(this.primaryResidentId);
        lease.setStartDate(this.startDate);
        lease.setEndDate(this.endDate);
        lease.setMonthlyRent(this.monthlyRent);
        lease.setSecurityDeposit(this.securityDeposit);
        lease.setPaymentDueDay(this.paymentDueDay);
        lease.setStatus(this.status);
        lease.setTermsConditions(this.termsConditions);
        lease.setSpecialClauses(this.specialClauses);
        lease.setSignedDate(this.signedDate);
        lease.setTerminationDate(this.terminationDate);
        lease.setTerminationReason(this.terminationReason);
        lease.setNotes(this.notes);
        return lease;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPaymentDueDay() {
        return paymentDueDay;
    }

    public void setPaymentDueDay(Integer paymentDueDay) {
        this.paymentDueDay = paymentDueDay;
    }

    public Lease.LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(Lease.LeaseStatus status) {
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
