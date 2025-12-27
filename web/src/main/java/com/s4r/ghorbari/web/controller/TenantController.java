package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.TenantDto;
import com.s4r.ghorbari.core.service.ITenantService;
import com.s4r.ghorbari.web.dto.TenantRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tenants", description = "Tenant management endpoints (Super Admin only)")
@RestController
public class TenantController {

    private final ITenantService tenantService;

    public TenantController(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    // PUBLIC ENDPOINT - Get tenant by key
    @Operation(summary = "Get tenant by key (Public)", description = "Retrieve tenant information by tenant key - no authentication required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant found",
                    content = @Content(schema = @Schema(implementation = TenantDto.class))),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/api/public/tenants/{tenantKey}")
    public ResponseEntity<TenantDto> getTenantByKey(
            @Parameter(description = "Tenant key") @PathVariable String tenantKey) {
        return tenantService.getActiveTenantByKey(tenantKey)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN ENDPOINTS - Only SUPER_ADMIN can access
    @Operation(summary = "Create new tenant", description = "Create a new tenant in the system (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tenant created successfully",
                    content = @Content(schema = @Schema(implementation = TenantDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/api/tenants")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDto> createTenant(@Valid @RequestBody TenantRequest request) {
        TenantDto dto = mapToDto(request);
        TenantDto createdTenant = tenantService.createTenant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTenant);
    }

    @Operation(summary = "Get all tenants", description = "Retrieve all tenants (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenants retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/api/tenants")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<TenantDto>> getAllTenants() {
        List<TenantDto> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @Operation(summary = "Get tenant by ID", description = "Retrieve a specific tenant by ID (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant found",
                    content = @Content(schema = @Schema(implementation = TenantDto.class))),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/api/tenants/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDto> getTenantById(
            @Parameter(description = "Tenant ID") @PathVariable Long id) {
        return tenantService.getTenantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update tenant", description = "Update an existing tenant (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant updated successfully",
                    content = @Content(schema = @Schema(implementation = TenantDto.class))),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/api/tenants/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDto> updateTenant(
            @Parameter(description = "Tenant ID") @PathVariable Long id,
            @Valid @RequestBody TenantRequest request) {
        TenantDto dto = mapToDto(request);
        TenantDto updatedTenant = tenantService.updateTenant(id, dto);
        return ResponseEntity.ok(updatedTenant);
    }

    @Operation(summary = "Delete tenant", description = "Delete a tenant from the system (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/api/tenants/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteTenant(
            @Parameter(description = "Tenant ID") @PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activate tenant", description = "Activate a tenant (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenant activated successfully"),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/api/tenants/{id}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> activateTenant(
            @Parameter(description = "Tenant ID") @PathVariable Long id) {
        tenantService.activateTenant(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate tenant", description = "Deactivate a tenant (Super Admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenant deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Super Admin role required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/api/tenants/{id}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deactivateTenant(
            @Parameter(description = "Tenant ID") @PathVariable Long id) {
        tenantService.deactivateTenant(id);
        return ResponseEntity.noContent().build();
    }

    private TenantDto mapToDto(TenantRequest request) {
        TenantDto dto = new TenantDto();
        dto.setTenantKey(request.getTenantKey());
        dto.setName(request.getName());
        dto.setActive(request.getActive());
        dto.setDescription(request.getDescription());
        return dto;
    }
}
