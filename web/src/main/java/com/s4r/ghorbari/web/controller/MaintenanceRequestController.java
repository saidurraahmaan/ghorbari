package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.entity.MaintenanceRequest;
import com.s4r.ghorbari.core.service.IMaintenanceRequestService;
import com.s4r.ghorbari.web.dto.MaintenanceRequestRequest;
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

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Maintenance Requests", description = "Maintenance request management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {

    private final IMaintenanceRequestService maintenanceRequestService;

    public MaintenanceRequestController(IMaintenanceRequestService maintenanceRequestService) {
        this.maintenanceRequestService = maintenanceRequestService;
    }

    @Operation(summary = "Create maintenance request", description = "Create a new maintenance request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createRequest(@Valid @RequestBody MaintenanceRequestRequest request) {
        MaintenanceRequest maintenanceRequest = mapToEntity(request);
        maintenanceRequestService.createRequest(maintenanceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all requests", description = "Retrieve all maintenance requests for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<MaintenanceRequest>> getAllRequests() {
        List<MaintenanceRequest> requests = maintenanceRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get request by ID", description = "Retrieve a specific maintenance request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request found",
                    content = @Content(schema = @Schema(implementation = MaintenanceRequest.class))),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRequest> getRequestById(
            @Parameter(description = "Request ID") @PathVariable Long id) {
        return maintenanceRequestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get request by number", description = "Retrieve a specific maintenance request by its request number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request found",
                    content = @Content(schema = @Schema(implementation = MaintenanceRequest.class))),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/number/{requestNumber}")
    public ResponseEntity<MaintenanceRequest> getRequestByNumber(
            @Parameter(description = "Request number") @PathVariable String requestNumber) {
        return maintenanceRequestService.getRequestByNumber(requestNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get requests by status", description = "Retrieve maintenance requests filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByStatus(
            @Parameter(description = "Request status (OPEN, ASSIGNED, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED, REJECTED)")
            @PathVariable MaintenanceRequest.RequestStatus status) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get requests by priority", description = "Retrieve maintenance requests filtered by priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByPriority(
            @Parameter(description = "Priority (LOW, MEDIUM, HIGH, URGENT)")
            @PathVariable MaintenanceRequest.Priority priority) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByPriority(priority);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get requests by category", description = "Retrieve maintenance requests filtered by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByCategory(
            @Parameter(description = "Category (PLUMBING, ELECTRICAL, HVAC, etc.)")
            @PathVariable MaintenanceRequest.RequestCategory category) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByCategory(category);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get requests by apartment", description = "Retrieve maintenance requests for a specific apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long apartmentId) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByApartmentId(apartmentId);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get requests by resident", description = "Retrieve maintenance requests created by a specific resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByResident(
            @Parameter(description = "Resident ID") @PathVariable Long residentId) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByResidentId(residentId);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get requests by assigned staff", description = "Retrieve maintenance requests assigned to a specific staff member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/assigned/{staffId}")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByAssignedTo(
            @Parameter(description = "Staff ID") @PathVariable Long staffId) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByAssignedTo(staffId);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get active requests", description = "Retrieve all active (OPEN, ASSIGNED, IN_PROGRESS) maintenance requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/active")
    public ResponseEntity<List<MaintenanceRequest>> getActiveRequests() {
        List<MaintenanceRequest> requests = maintenanceRequestService.getActiveRequests();
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get requests by date range", description = "Retrieve maintenance requests within a specific date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requests retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<MaintenanceRequest>> getRequestsByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<MaintenanceRequest> requests = maintenanceRequestService.getRequestsByDateRange(startDate, endDate);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Update request", description = "Update an existing maintenance request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request updated successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(
            @Parameter(description = "Request ID") @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequestRequest request) {
        MaintenanceRequest maintenanceRequest = mapToEntity(request);
        maintenanceRequest.setId(id);
        maintenanceRequestService.updateRequest(maintenanceRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign to staff", description = "Assign a maintenance request to a staff member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignToStaff(
            @Parameter(description = "Request ID") @PathVariable Long id,
            @Parameter(description = "Staff ID") @RequestParam Long staffId) {
        maintenanceRequestService.assignToStaff(id, staffId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update status", description = "Update the status of a maintenance request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @Parameter(description = "Request ID") @PathVariable Long id,
            @Parameter(description = "New status") @RequestParam MaintenanceRequest.RequestStatus status) {
        maintenanceRequestService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Schedule request", description = "Schedule a maintenance request for a specific date and time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request scheduled successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/schedule")
    public ResponseEntity<?> scheduleRequest(
            @Parameter(description = "Request ID") @PathVariable Long id,
            @Parameter(description = "Scheduled date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledDate) {
        maintenanceRequestService.scheduleRequest(id, scheduledDate);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Complete request", description = "Mark a maintenance request as completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request completed successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeRequest(
            @Parameter(description = "Request ID") @PathVariable Long id,
            @Parameter(description = "Resolution notes") @RequestParam(required = false) String resolutionNotes) {
        maintenanceRequestService.completeRequest(id, resolutionNotes);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel request", description = "Cancel a maintenance request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelRequest(
            @Parameter(description = "Request ID") @PathVariable Long id,
            @Parameter(description = "Cancellation reason") @RequestParam(required = false) String reason) {
        maintenanceRequestService.cancelRequest(id, reason);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete request", description = "Delete a maintenance request from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(
            @Parameter(description = "Request ID") @PathVariable Long id) {
        maintenanceRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    private MaintenanceRequest mapToEntity(MaintenanceRequestRequest request) {
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.setRequestNumber(request.getRequestNumber());
        maintenanceRequest.setApartmentId(request.getApartmentId());
        maintenanceRequest.setResidentId(request.getResidentId());
        maintenanceRequest.setTitle(request.getTitle());
        maintenanceRequest.setDescription(request.getDescription());
        maintenanceRequest.setCategory(request.getCategory());
        if (request.getPriority() != null) {
            maintenanceRequest.setPriority(request.getPriority());
        }
        if (request.getStatus() != null) {
            maintenanceRequest.setStatus(request.getStatus());
        }
        maintenanceRequest.setAssignedToId(request.getAssignedToId());
        maintenanceRequest.setScheduledDate(request.getScheduledDate());
        maintenanceRequest.setNotes(request.getNotes());
        return maintenanceRequest;
    }
}
