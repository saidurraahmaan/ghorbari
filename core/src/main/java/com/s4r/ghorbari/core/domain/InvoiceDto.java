package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDto {

    private Long id;
    private String invoiceNumber;
    private Long leaseId;
    private Long residentId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private Invoice.InvoiceStatus status;
    private String description;
    private BigDecimal rentAmount;
    private BigDecimal utilityCharges;
    private BigDecimal maintenanceCharges;
    private BigDecimal lateFees;
    private BigDecimal otherCharges;
    private LocalDate paidDate;
    private String notes;

    // Constructors
    public InvoiceDto() {
    }

    public InvoiceDto(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceNumber = invoice.getInvoiceNumber();
        this.leaseId = invoice.getLeaseId();
        this.residentId = invoice.getResidentId();
        this.issueDate = invoice.getIssueDate();
        this.dueDate = invoice.getDueDate();
        this.totalAmount = invoice.getTotalAmount();
        this.paidAmount = invoice.getPaidAmount();
        this.status = invoice.getStatus();
        this.description = invoice.getDescription();
        this.rentAmount = invoice.getRentAmount();
        this.utilityCharges = invoice.getUtilityCharges();
        this.maintenanceCharges = invoice.getMaintenanceCharges();
        this.lateFees = invoice.getLateFees();
        this.otherCharges = invoice.getOtherCharges();
        this.paidDate = invoice.getPaidDate();
        this.notes = invoice.getNotes();
    }

    public Invoice toEntity() {
        Invoice invoice = new Invoice();
        invoice.setId(this.id);
        invoice.setInvoiceNumber(this.invoiceNumber);
        invoice.setLeaseId(this.leaseId);
        invoice.setResidentId(this.residentId);
        invoice.setIssueDate(this.issueDate);
        invoice.setDueDate(this.dueDate);
        invoice.setTotalAmount(this.totalAmount);
        invoice.setPaidAmount(this.paidAmount);
        invoice.setStatus(this.status);
        invoice.setDescription(this.description);
        invoice.setRentAmount(this.rentAmount);
        invoice.setUtilityCharges(this.utilityCharges);
        invoice.setMaintenanceCharges(this.maintenanceCharges);
        invoice.setLateFees(this.lateFees);
        invoice.setOtherCharges(this.otherCharges);
        invoice.setPaidDate(this.paidDate);
        invoice.setNotes(this.notes);
        return invoice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Invoice.InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(Invoice.InvoiceStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public BigDecimal getUtilityCharges() {
        return utilityCharges;
    }

    public void setUtilityCharges(BigDecimal utilityCharges) {
        this.utilityCharges = utilityCharges;
    }

    public BigDecimal getMaintenanceCharges() {
        return maintenanceCharges;
    }

    public void setMaintenanceCharges(BigDecimal maintenanceCharges) {
        this.maintenanceCharges = maintenanceCharges;
    }

    public BigDecimal getLateFees() {
        return lateFees;
    }

    public void setLateFees(BigDecimal lateFees) {
        this.lateFees = lateFees;
    }

    public BigDecimal getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(BigDecimal otherCharges) {
        this.otherCharges = otherCharges;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
