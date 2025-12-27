package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.PaymentDto;
import com.s4r.ghorbari.core.entity.Payment;
import com.s4r.ghorbari.core.service.IPaymentService;
import com.s4r.ghorbari.web.dto.PaymentRequest;
import com.s4r.ghorbari.web.dto.RefundRequest;
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

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Payments", description = "Payment processing and management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Record new payment", description = "Record a new payment in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> recordPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentDto dto = mapToDto(request);
        paymentService.recordPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all payments", description = "Retrieve all payments for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payment by ID", description = "Retrieve a specific payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(
            @Parameter(description = "Payment ID") @PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get payment by reference", description = "Retrieve a specific payment by its reference number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/reference/{paymentReference}")
    public ResponseEntity<PaymentDto> getPaymentByReference(
            @Parameter(description = "Payment reference") @PathVariable String paymentReference) {
        return paymentService.getPaymentByReference(paymentReference)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get payments by invoice", description = "Retrieve all payments for a specific invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByInvoice(
            @Parameter(description = "Invoice ID") @PathVariable Long invoiceId) {
        List<PaymentDto> payments = paymentService.getPaymentsByInvoiceId(invoiceId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by resident", description = "Retrieve all payments made by a specific resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByResident(
            @Parameter(description = "Resident ID") @PathVariable Long residentId) {
        List<PaymentDto> payments = paymentService.getPaymentsByResidentId(residentId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by status", description = "Retrieve payments filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStatus(
            @Parameter(description = "Payment status (PENDING, COMPLETED, FAILED, REFUNDED, PARTIALLY_REFUNDED, CANCELLED)")
            @PathVariable Payment.PaymentStatus status) {
        List<PaymentDto> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by method", description = "Retrieve payments filtered by payment method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByMethod(
            @Parameter(description = "Payment method (CASH, BANK_TRANSFER, CREDIT_CARD, etc.)")
            @PathVariable Payment.PaymentMethod paymentMethod) {
        List<PaymentDto> payments = paymentService.getPaymentsByMethod(paymentMethod);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by date range", description = "Retrieve payments within a specific date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentDto>> getPaymentsByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PaymentDto> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Update payment", description = "Update an existing payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(
            @Parameter(description = "Payment ID") @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request) {
        PaymentDto dto = mapToDto(request);
        paymentService.updatePayment(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Refund payment", description = "Process a refund for a payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Refund processed successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid refund amount",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/refund")
    public ResponseEntity<?> refundPayment(
            @Parameter(description = "Payment ID") @PathVariable Long id,
            @Valid @RequestBody RefundRequest request) {
        paymentService.refundPayment(id, request.getRefundAmount(), request.getReason(), request.getRefundDate());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel payment", description = "Cancel a payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelPayment(
            @Parameter(description = "Payment ID") @PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete payment", description = "Delete a payment from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(
            @Parameter(description = "Payment ID") @PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    private PaymentDto mapToDto(PaymentRequest request) {
        PaymentDto dto = new PaymentDto();
        dto.setPaymentReference(request.getPaymentReference());
        dto.setInvoiceId(request.getInvoiceId());
        dto.setResidentId(request.getResidentId());
        dto.setPaymentDate(request.getPaymentDate());
        dto.setAmount(request.getAmount());
        dto.setPaymentMethod(request.getPaymentMethod());
        dto.setStatus(request.getStatus());
        dto.setTransactionId(request.getTransactionId());
        dto.setDescription(request.getDescription());
        dto.setNotes(request.getNotes());
        return dto;
    }
}
