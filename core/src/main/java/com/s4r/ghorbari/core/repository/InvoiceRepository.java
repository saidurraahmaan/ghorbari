package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByStatus(Invoice.InvoiceStatus status);

    List<Invoice> findByLeaseId(Long leaseId);

    List<Invoice> findByResidentId(Long residentId);

    @Query("SELECT i FROM Invoice i WHERE i.status = 'PENDING' OR i.status = 'PARTIALLY_PAID'")
    List<Invoice> findUnpaidInvoices();

    @Query("SELECT i FROM Invoice i WHERE i.dueDate < :currentDate AND (i.status = 'PENDING' OR i.status = 'PARTIALLY_PAID')")
    List<Invoice> findOverdueInvoices(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT i FROM Invoice i WHERE i.issueDate BETWEEN :startDate AND :endDate")
    List<Invoice> findInvoicesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
