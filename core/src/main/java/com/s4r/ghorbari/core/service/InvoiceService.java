package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.InvoiceDto;
import com.s4r.ghorbari.core.entity.Invoice;
import com.s4r.ghorbari.core.entity.Lease;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.InvoiceRepository;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import com.s4r.ghorbari.core.repository.LeaseRepository;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceService implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final LeaseRepository leaseRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, LeaseRepository leaseRepository) {
        this.invoiceRepository = invoiceRepository;
        this.leaseRepository = leaseRepository;
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void createInvoice(InvoiceDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Invoice invoice = dto.toEntity();

        // Generate invoice number if not provided
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            invoice.setInvoiceNumber(generateInvoiceNumber());
        }

        invoice.setTenantId(tenantId);
        invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceDto> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InvoiceDto> getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(InvoiceDto::new);
    }

    @Override
    public Optional<InvoiceDto> getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .map(InvoiceDto::new);
    }

    @Override
    public List<InvoiceDto> getInvoicesByStatus(Invoice.InvoiceStatus status) {
        return invoiceRepository.findByStatus(status).stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getInvoicesByLeaseId(Long leaseId) {
        return invoiceRepository.findByLeaseId(leaseId).stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getInvoicesByResidentId(Long residentId) {
        return invoiceRepository.findByResidentId(residentId).stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getUnpaidInvoices() {
        return invoiceRepository.findUnpaidInvoices().stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getOverdueInvoices() {
        return invoiceRepository.findOverdueInvoices(LocalDate.now()).stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findInvoicesByDateRange(startDate, endDate).stream()
                .map(InvoiceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateInvoice(Long id, InvoiceDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        invoiceRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Invoice not found"));

        Invoice invoice = dto.toEntity();
        invoice.setId(id);
        invoice.setTenantId(tenantId);
        invoiceRepository.save(invoice);
    }

    @Override
    public void markAsPaid(Long id, LocalDate paidDate) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Invoice not found"));

        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoice.setPaidAmount(invoice.getTotalAmount());
        invoice.setPaidDate(paidDate);
        invoiceRepository.save(invoice);
    }

    @Override
    public void cancelInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Invoice not found"));

        invoice.setStatus(Invoice.InvoiceStatus.CANCELLED);
        invoiceRepository.save(invoice);
    }

    @Override
    public void generateMonthlyInvoices(YearMonth month) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        // Find all active leases
        List<Lease> activeLeases = leaseRepository.findByStatus(Lease.LeaseStatus.ACTIVE);

        LocalDate issueDate = month.atDay(1);
        LocalDate dueDate = month.atDay(5); // Due on 5th of the month

        for (Lease lease : activeLeases) {
            // Check if invoice already exists for this lease and month
            boolean invoiceExists = invoiceRepository.findByLeaseId(lease.getId())
                    .stream()
                    .anyMatch(inv -> inv.getIssueDate().getYear() == month.getYear() &&
                            inv.getIssueDate().getMonth() == month.getMonth());

            if (!invoiceExists) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceNumber(generateInvoiceNumber());
                invoice.setLeaseId(lease.getId());
                invoice.setResidentId(lease.getPrimaryResidentId());
                invoice.setIssueDate(issueDate);
                invoice.setDueDate(dueDate);
                invoice.setRentAmount(lease.getMonthlyRent());
                invoice.setTotalAmount(lease.getMonthlyRent());
                invoice.setPaidAmount(BigDecimal.ZERO);
                invoice.setStatus(Invoice.InvoiceStatus.PENDING);
                invoice.setDescription("Monthly rent for " + month.getMonth() + " " + month.getYear());
                invoice.setTenantId(tenantId);

                invoiceRepository.save(invoice);
            }
        }
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Invoice not found"));

        invoiceRepository.delete(invoice);
    }

    private String generateInvoiceNumber() {
        return "INV-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
