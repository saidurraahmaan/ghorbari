package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.PaymentDto;
import com.s4r.ghorbari.core.entity.Invoice;
import com.s4r.ghorbari.core.entity.Payment;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.InvoiceRepository;
import com.s4r.ghorbari.core.entity.Role.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import com.s4r.ghorbari.core.repository.PaymentRepository;
import com.s4r.ghorbari.core.entity.Role.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void recordPayment(PaymentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Payment payment = dto.toEntity();

        // Generate payment reference if not provided
        if (payment.getPaymentReference() == null || payment.getPaymentReference().isEmpty()) {
            payment.setPaymentReference(generatePaymentReference());
        }

        payment.setTenantId(tenantId);
        paymentRepository.save(payment);

        // Update invoice status if payment is linked to an invoice
        if (payment.getInvoiceId() != null && payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
            updateInvoiceAfterPayment(payment.getInvoiceId());
        }
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentDto> getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentDto::new);
    }

    @Override
    public Optional<PaymentDto> getPaymentByReference(String paymentReference) {
        return paymentRepository.findByPaymentReference(paymentReference)
                .map(PaymentDto::new);
    }

    @Override
    public List<PaymentDto> getPaymentsByInvoiceId(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId).stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByResidentId(Long residentId) {
        return paymentRepository.findByResidentId(residentId).stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByMethod(Payment.PaymentMethod paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod).stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findPaymentsByDateRange(startDate, endDate).stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByResidentAndDateRange(Long residentId, LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findPaymentsByResidentAndDateRange(residentId, startDate, endDate).stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updatePayment(Long id, PaymentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        paymentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Payment not found"));

        Payment payment = dto.toEntity();
        payment.setId(id);
        payment.setTenantId(tenantId);
        paymentRepository.save(payment);
    }

    @Override
    public void refundPayment(Long id, BigDecimal refundAmount, String reason, LocalDate refundDate) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Payment not found"));

        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new ServiceException(ErrorCode.INVALID_OPERATION, "Refund amount cannot exceed payment amount");
        }

        payment.setRefundAmount(refundAmount);
        payment.setRefundReason(reason);
        payment.setRefundDate(refundDate != null ? refundDate : LocalDate.now());

        if (refundAmount.compareTo(payment.getAmount()) == 0) {
            payment.setStatus(Payment.PaymentStatus.REFUNDED);
        } else {
            payment.setStatus(Payment.PaymentStatus.PARTIALLY_REFUNDED);
        }

        paymentRepository.save(payment);

        // Update invoice status if payment is linked to an invoice
        if (payment.getInvoiceId() != null) {
            updateInvoiceAfterPayment(payment.getInvoiceId());
        }
    }

    @Override
    public void cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Payment not found"));

        payment.setStatus(Payment.PaymentStatus.CANCELLED);
        paymentRepository.save(payment);

        // Update invoice status if payment is linked to an invoice
        if (payment.getInvoiceId() != null) {
            updateInvoiceAfterPayment(payment.getInvoiceId());
        }
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Payment not found"));

        paymentRepository.delete(payment);
    }

    private void updateInvoiceAfterPayment(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Invoice not found"));

        // Calculate total paid amount from all completed payments
        List<Payment> payments = paymentRepository.findByInvoiceId(invoiceId);
        BigDecimal totalPaid = payments.stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.COMPLETED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Subtract refunded amounts
        BigDecimal totalRefunded = payments.stream()
                .filter(p -> p.getRefundAmount() != null)
                .map(Payment::getRefundAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netPaid = totalPaid.subtract(totalRefunded);

        invoice.setPaidAmount(netPaid);

        // Update invoice status
        if (netPaid.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setStatus(Invoice.InvoiceStatus.PENDING);
        } else if (netPaid.compareTo(invoice.getTotalAmount()) >= 0) {
            invoice.setStatus(Invoice.InvoiceStatus.PAID);
        } else {
            invoice.setStatus(Invoice.InvoiceStatus.PARTIALLY_PAID);
        }

        invoiceRepository.save(invoice);
    }

    private String generatePaymentReference() {
        return "PAY-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
