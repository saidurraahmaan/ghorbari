package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Lease;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseDto {

    private Long id;
    private Long apartmentId;
    private Long primaryResidentId;
    private Lease.LeaseType leaseType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer noticePeriodMonths;
    private Integer advancePaymentMonths;
    private LocalDate noticeGivenDate;
    private BigDecimal monthlyRent;
    private BigDecimal securityDeposit;
    private Lease.LeaseStatus status;
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
        this.leaseType = lease.getLeaseType();
        this.startDate = lease.getStartDate();
        this.endDate = lease.getEndDate();
        this.noticePeriodMonths = lease.getNoticePeriodMonths();
        this.advancePaymentMonths = lease.getAdvancePaymentMonths();
        this.noticeGivenDate = lease.getNoticeGivenDate();
        this.monthlyRent = lease.getMonthlyRent();
        this.securityDeposit = lease.getSecurityDeposit();
        this.status = lease.getStatus();
        this.terminationDate = lease.getTerminationDate();
        this.terminationReason = lease.getTerminationReason();
        this.notes = lease.getNotes();
    }

    public Lease toEntity() {
        Lease lease = new Lease();
        lease.setId(this.id);
        lease.setApartmentId(this.apartmentId);
        lease.setPrimaryResidentId(this.primaryResidentId);
        lease.setLeaseType(this.leaseType);
        lease.setStartDate(this.startDate);
        lease.setEndDate(this.endDate);
        lease.setNoticePeriodMonths(this.noticePeriodMonths);
        lease.setAdvancePaymentMonths(this.advancePaymentMonths);
        lease.setNoticeGivenDate(this.noticeGivenDate);
        lease.setMonthlyRent(this.monthlyRent);
        lease.setSecurityDeposit(this.securityDeposit);
        lease.setStatus(this.status);
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

    public Lease.LeaseType getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(Lease.LeaseType leaseType) {
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

    public Lease.LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(Lease.LeaseStatus status) {
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
