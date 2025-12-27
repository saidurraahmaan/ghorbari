package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.InvoiceDto;
import com.s4r.ghorbari.core.entity.Invoice;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface IInvoiceService {

    void createInvoice(InvoiceDto dto);

    List<InvoiceDto> getAllInvoices();

    Optional<InvoiceDto> getInvoiceById(Long id);

    Optional<InvoiceDto> getInvoiceByNumber(String invoiceNumber);

    List<InvoiceDto> getInvoicesByStatus(Invoice.InvoiceStatus status);

    List<InvoiceDto> getInvoicesByLeaseId(Long leaseId);

    List<InvoiceDto> getInvoicesByResidentId(Long residentId);

    List<InvoiceDto> getUnpaidInvoices();

    List<InvoiceDto> getOverdueInvoices();

    List<InvoiceDto> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate);

    void updateInvoice(Long id, InvoiceDto dto);

    void markAsPaid(Long id, LocalDate paidDate);

    void cancelInvoice(Long id);

    void generateMonthlyInvoices(YearMonth month);

    void deleteInvoice(Long id);
}
