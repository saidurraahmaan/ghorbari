package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.PaymentDto;
import com.s4r.ghorbari.core.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    void recordPayment(PaymentDto dto);

    List<PaymentDto> getAllPayments();

    Optional<PaymentDto> getPaymentById(Long id);

    Optional<PaymentDto> getPaymentByReference(String paymentReference);

    List<PaymentDto> getPaymentsByInvoiceId(Long invoiceId);

    List<PaymentDto> getPaymentsByResidentId(Long residentId);

    List<PaymentDto> getPaymentsByStatus(Payment.PaymentStatus status);

    List<PaymentDto> getPaymentsByMethod(Payment.PaymentMethod paymentMethod);

    List<PaymentDto> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate);

    List<PaymentDto> getPaymentsByResidentAndDateRange(Long residentId, LocalDate startDate, LocalDate endDate);

    void updatePayment(Long id, PaymentDto dto);

    void refundPayment(Long id, BigDecimal refundAmount, String reason, LocalDate refundDate);

    void cancelPayment(Long id);

    void deletePayment(Long id);
}
