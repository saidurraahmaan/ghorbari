package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.InvoiceDto;
import com.s4r.ghorbari.core.entity.Invoice;
import com.s4r.ghorbari.core.service.IInvoiceService;
import com.s4r.ghorbari.web.dto.InvoiceRequest;
import com.s4r.ghorbari.web.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Tag(name = "Invoices", description = "Invoice and billing management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final IInvoiceService invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "Create new invoice", description = "Add a new invoice to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        InvoiceDto dto = mapToDto(request);
        invoiceService.createInvoice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all invoices", description = "Retrieve all invoices for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceDto> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get invoice by ID", description = "Retrieve a specific invoice by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found",
                    content = @Content(schema = @Schema(implementation = InvoiceDto.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(
            @Parameter(description = "Invoice ID") @PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get invoice by number", description = "Retrieve a specific invoice by its invoice number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found",
                    content = @Content(schema = @Schema(implementation = InvoiceDto.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<InvoiceDto> getInvoiceByNumber(
            @Parameter(description = "Invoice number") @PathVariable String invoiceNumber) {
        return invoiceService.getInvoiceByNumber(invoiceNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get invoices by status", description = "Retrieve invoices filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByStatus(
            @Parameter(description = "Invoice status (PENDING, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED)")
            @PathVariable Invoice.InvoiceStatus status) {
        List<InvoiceDto> invoices = invoiceService.getInvoicesByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get invoices by lease", description = "Retrieve all invoices for a specific lease")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/lease/{leaseId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByLease(
            @Parameter(description = "Lease ID") @PathVariable Long leaseId) {
        List<InvoiceDto> invoices = invoiceService.getInvoicesByLeaseId(leaseId);
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get invoices by resident", description = "Retrieve all invoices for a specific resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByResident(
            @Parameter(description = "Resident ID") @PathVariable Long residentId) {
        List<InvoiceDto> invoices = invoiceService.getInvoicesByResidentId(residentId);
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get unpaid invoices", description = "Retrieve all unpaid or partially paid invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/unpaid")
    public ResponseEntity<List<InvoiceDto>> getUnpaidInvoices() {
        List<InvoiceDto> invoices = invoiceService.getUnpaidInvoices();
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get overdue invoices", description = "Retrieve all overdue invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/overdue")
    public ResponseEntity<List<InvoiceDto>> getOverdueInvoices() {
        List<InvoiceDto> invoices = invoiceService.getOverdueInvoices();
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get invoices by date range", description = "Retrieve invoices within a specific date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<InvoiceDto> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Update invoice", description = "Update an existing invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice updated successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(
            @Parameter(description = "Invoice ID") @PathVariable Long id,
            @Valid @RequestBody InvoiceRequest request) {
        InvoiceDto dto = mapToDto(request);
        invoiceService.updateInvoice(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark invoice as paid", description = "Mark an invoice as fully paid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice marked as paid successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/mark-paid")
    public ResponseEntity<?> markAsPaid(
            @Parameter(description = "Invoice ID") @PathVariable Long id,
            @Parameter(description = "Payment date (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate paidDate) {
        LocalDate paymentDate = paidDate != null ? paidDate : LocalDate.now();
        invoiceService.markAsPaid(id, paymentDate);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel invoice", description = "Cancel an invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelInvoice(
            @Parameter(description = "Invoice ID") @PathVariable Long id) {
        invoiceService.cancelInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Generate monthly invoices", description = "Generate invoices for all active leases for a specific month")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Monthly invoices generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/generate-monthly")
    public ResponseEntity<?> generateMonthlyInvoices(
            @Parameter(description = "Year-Month (yyyy-MM)")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        invoiceService.generateMonthlyInvoices(month);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete invoice", description = "Delete an invoice from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(
            @Parameter(description = "Invoice ID") @PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    private InvoiceDto mapToDto(InvoiceRequest request) {
        InvoiceDto dto = new InvoiceDto();
        dto.setInvoiceNumber(request.getInvoiceNumber());
        dto.setLeaseId(request.getLeaseId());
        dto.setResidentId(request.getResidentId());
        dto.setIssueDate(request.getIssueDate());
        dto.setDueDate(request.getDueDate());
        dto.setTotalAmount(request.getTotalAmount());
        dto.setPaidAmount(BigDecimal.ZERO);
        dto.setStatus(request.getStatus());
        dto.setDescription(request.getDescription());
        dto.setRentAmount(request.getRentAmount());
        dto.setUtilityCharges(request.getUtilityCharges());
        dto.setMaintenanceCharges(request.getMaintenanceCharges());
        dto.setLateFees(request.getLateFees());
        dto.setOtherCharges(request.getOtherCharges());
        dto.setNotes(request.getNotes());
        return dto;
    }
}
