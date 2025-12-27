package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDto {

    private Long id;
    private String paymentReference;
    private Long invoiceId;
    private Long residentId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private Payment.PaymentMethod paymentMethod;
    private Payment.PaymentStatus status;
    private String transactionId;
    private String description;
    private String notes;
    private BigDecimal refundAmount;
    private LocalDate refundDate;
    private String refundReason;

    // Constructors
    public PaymentDto() {
    }

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.paymentReference = payment.getPaymentReference();
        this.invoiceId = payment.getInvoiceId();
        this.residentId = payment.getResidentId();
        this.paymentDate = payment.getPaymentDate();
        this.amount = payment.getAmount();
        this.paymentMethod = payment.getPaymentMethod();
        this.status = payment.getStatus();
        this.transactionId = payment.getTransactionId();
        this.description = payment.getDescription();
        this.notes = payment.getNotes();
        this.refundAmount = payment.getRefundAmount();
        this.refundDate = payment.getRefundDate();
        this.refundReason = payment.getRefundReason();
    }

    public Payment toEntity() {
        Payment payment = new Payment();
        payment.setId(this.id);
        payment.setPaymentReference(this.paymentReference);
        payment.setInvoiceId(this.invoiceId);
        payment.setResidentId(this.residentId);
        payment.setPaymentDate(this.paymentDate);
        payment.setAmount(this.amount);
        payment.setPaymentMethod(this.paymentMethod);
        payment.setStatus(this.status);
        payment.setTransactionId(this.transactionId);
        payment.setDescription(this.description);
        payment.setNotes(this.notes);
        payment.setRefundAmount(this.refundAmount);
        payment.setRefundDate(this.refundDate);
        payment.setRefundReason(this.refundReason);
        return payment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Payment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Payment.PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(Payment.PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
}
