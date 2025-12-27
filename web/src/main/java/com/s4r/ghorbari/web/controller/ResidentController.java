package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.ResidentDto;
import com.s4r.ghorbari.core.entity.Resident;
import com.s4r.ghorbari.core.service.IResidentService;
import com.s4r.ghorbari.web.dto.ResidentRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Residents", description = "Resident/Tenant management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final IResidentService residentService;

    public ResidentController(IResidentService residentService) {
        this.residentService = residentService;
    }

    @Operation(summary = "Create new resident", description = "Add a new resident to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resident created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createResident(@Valid @RequestBody ResidentRequest request) {
        ResidentDto dto = mapToDto(request);
        residentService.createResident(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all residents", description = "Retrieve all residents for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Residents retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ResidentDto>> getAllResidents() {
        List<ResidentDto> residents = residentService.getAllResidents();
        return ResponseEntity.ok(residents);
    }

    @Operation(summary = "Get resident by ID", description = "Retrieve a specific resident by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resident found",
                    content = @Content(schema = @Schema(implementation = ResidentDto.class))),
            @ApiResponse(responseCode = "404", description = "Resident not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResidentDto> getResidentById(
            @Parameter(description = "Resident ID") @PathVariable Long id) {
        return residentService.getResidentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get residents by status", description = "Retrieve residents filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Residents retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ResidentDto>> getResidentsByStatus(
            @Parameter(description = "Resident status (ACTIVE, INACTIVE, EVICTED)")
            @PathVariable Resident.ResidentStatus status) {
        List<ResidentDto> residents = residentService.getResidentsByStatus(status);
        return ResponseEntity.ok(residents);
    }

    @Operation(summary = "Get residents by apartment", description = "Retrieve all residents in a specific apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Residents retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<ResidentDto>> getResidentsByApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long apartmentId) {
        List<ResidentDto> residents = residentService.getResidentsByApartmentId(apartmentId);
        return ResponseEntity.ok(residents);
    }

    @Operation(summary = "Update resident", description = "Update an existing resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resident updated successfully"),
            @ApiResponse(responseCode = "404", description = "Resident not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResident(
            @Parameter(description = "Resident ID") @PathVariable Long id,
            @Valid @RequestBody ResidentRequest request) {
        ResidentDto dto = mapToDto(request);
        residentService.updateResident(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete resident", description = "Delete a resident from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resident deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resident not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResident(
            @Parameter(description = "Resident ID") @PathVariable Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }

    private ResidentDto mapToDto(ResidentRequest request) {
        ResidentDto dto = new ResidentDto();
        dto.setFirstName(request.getFirstName());
        dto.setLastName(request.getLastName());
        dto.setEmail(request.getEmail());
        dto.setPhoneNumber(request.getPhoneNumber());
        dto.setDateOfBirth(request.getDateOfBirth());
        dto.setUserId(request.getUserId());
        dto.setNationalId(request.getNationalId());
        dto.setPassportNumber(request.getPassportNumber());
        dto.setPassportExpiryDate(request.getPassportExpiryDate());
        dto.setNationality(request.getNationality());
        dto.setEmergencyContactName(request.getEmergencyContactName());
        dto.setEmergencyContactPhone(request.getEmergencyContactPhone());
        dto.setEmergencyContactRelationship(request.getEmergencyContactRelationship());
        dto.setIsPrimaryResident(request.getIsPrimaryResident());
        dto.setMoveInDate(request.getMoveInDate());
        dto.setMoveOutDate(request.getMoveOutDate());
        dto.setStatus(request.getStatus());
        dto.setNotes(request.getNotes());
        return dto;
    }
}
