package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.LeaseDto;
import com.s4r.ghorbari.core.entity.Lease;
import com.s4r.ghorbari.core.service.ILeaseService;
import com.s4r.ghorbari.web.dto.LeaseRenewalRequest;
import com.s4r.ghorbari.web.dto.LeaseRequest;
import com.s4r.ghorbari.web.dto.LeaseTerminationRequest;
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

@Tag(name = "Leases", description = "Lease/Rental agreement management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final ILeaseService leaseService;

    public LeaseController(ILeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @Operation(summary = "Create new lease", description = "Add a new lease/rental agreement to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lease created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createLease(@Valid @RequestBody LeaseRequest request) {
        LeaseDto dto = mapToDto(request);
        leaseService.createLease(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all leases", description = "Retrieve all leases for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leases retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<LeaseDto>> getAllLeases() {
        List<LeaseDto> leases = leaseService.getAllLeases();
        return ResponseEntity.ok(leases);
    }

    @Operation(summary = "Get lease by ID", description = "Retrieve a specific lease by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease found",
                    content = @Content(schema = @Schema(implementation = LeaseDto.class))),
            @ApiResponse(responseCode = "404", description = "Lease not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LeaseDto> getLeaseById(
            @Parameter(description = "Lease ID") @PathVariable Long id) {
        return leaseService.getLeaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get leases by status", description = "Retrieve leases filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leases retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaseDto>> getLeasesByStatus(
            @Parameter(description = "Lease status (DRAFT, ACTIVE, EXPIRING_SOON, EXPIRED, TERMINATED, RENEWED)")
            @PathVariable Lease.LeaseStatus status) {
        List<LeaseDto> leases = leaseService.getLeasesByStatus(status);
        return ResponseEntity.ok(leases);
    }

    @Operation(summary = "Get leases by apartment", description = "Retrieve all leases for a specific apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leases retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<LeaseDto>> getLeasesByApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long apartmentId) {
        List<LeaseDto> leases = leaseService.getLeasesByApartmentId(apartmentId);
        return ResponseEntity.ok(leases);
    }

    @Operation(summary = "Get leases by resident", description = "Retrieve all leases for a specific resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leases retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<LeaseDto>> getLeasesByResident(
            @Parameter(description = "Resident ID") @PathVariable Long residentId) {
        List<LeaseDto> leases = leaseService.getLeasesByResidentId(residentId);
        return ResponseEntity.ok(leases);
    }

    @Operation(summary = "Get expiring leases", description = "Retrieve leases expiring before a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leases retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/expiring")
    public ResponseEntity<List<LeaseDto>> getExpiringLeases(
            @Parameter(description = "Before date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beforeDate) {
        List<LeaseDto> leases = leaseService.getExpiringLeases(beforeDate);
        return ResponseEntity.ok(leases);
    }

    @Operation(summary = "Get active lease for apartment", description = "Retrieve the currently active lease for a specific apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease found",
                    content = @Content(schema = @Schema(implementation = LeaseDto.class))),
            @ApiResponse(responseCode = "404", description = "No active lease found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/apartment/{apartmentId}/active")
    public ResponseEntity<LeaseDto> getActiveLeaseByApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long apartmentId) {
        return leaseService.getActiveLeaseByApartment(apartmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update lease", description = "Update an existing lease")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lease updated successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLease(
            @Parameter(description = "Lease ID") @PathVariable Long id,
            @Valid @RequestBody LeaseRequest request) {
        LeaseDto dto = mapToDto(request);
        leaseService.updateLease(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Terminate lease", description = "Terminate an existing lease")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lease terminated successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/terminate")
    public ResponseEntity<?> terminateLease(
            @Parameter(description = "Lease ID") @PathVariable Long id,
            @Valid @RequestBody LeaseTerminationRequest request) {
        leaseService.terminateLease(id, request.getTerminationDate(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Renew lease", description = "Renew an existing lease with a new end date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lease renewed successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/renew")
    public ResponseEntity<?> renewLease(
            @Parameter(description = "Lease ID") @PathVariable Long id,
            @Valid @RequestBody LeaseRenewalRequest request) {
        leaseService.renewLease(id, request.getNewEndDate());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete lease", description = "Delete a lease from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lease deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLease(
            @Parameter(description = "Lease ID") @PathVariable Long id) {
        leaseService.deleteLease(id);
        return ResponseEntity.noContent().build();
    }

    private LeaseDto mapToDto(LeaseRequest request) {
        LeaseDto dto = new LeaseDto();
        dto.setApartmentId(request.getApartmentId());
        dto.setPrimaryResidentId(request.getPrimaryResidentId());
        dto.setStartDate(request.getStartDate());
        dto.setEndDate(request.getEndDate());
        dto.setMonthlyRent(request.getMonthlyRent());
        dto.setSecurityDeposit(request.getSecurityDeposit());
        dto.setPaymentDueDay(request.getPaymentDueDay());
        dto.setStatus(request.getStatus());
        dto.setTermsConditions(request.getTermsConditions());
        dto.setSpecialClauses(request.getSpecialClauses());
        dto.setSignedDate(request.getSignedDate());
        dto.setNotes(request.getNotes());
        return dto;
    }
}
