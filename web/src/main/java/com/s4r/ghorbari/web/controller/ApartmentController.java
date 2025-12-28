package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.ApartmentDto;
import com.s4r.ghorbari.core.entity.Apartment;
import com.s4r.ghorbari.core.service.IApartmentService;
import com.s4r.ghorbari.web.dto.ApartmentRequest;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Apartments", description = "Apartment management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final IApartmentService apartmentService;

    public ApartmentController(IApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Operation(summary = "Create new apartment", description = "Add a new apartment to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Apartment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<?> createApartment(@Valid @RequestBody ApartmentRequest request) {
        ApartmentDto dto = mapToDto(request);
        apartmentService.createApartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all apartments", description = "Retrieve all apartments for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<ApartmentDto> apartments = apartmentService.getAllApartments();
        return ResponseEntity.ok(apartments);
    }

    @Operation(summary = "Get apartment by ID", description = "Retrieve a specific apartment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment found",
                    content = @Content(schema = @Schema(implementation = ApartmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Apartment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDto> getApartmentById(
            @Parameter(description = "Apartment ID") @PathVariable Long id) {
        return apartmentService.getApartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get apartments by status", description = "Retrieve apartments filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApartmentDto>> getApartmentsByStatus(
            @Parameter(description = "Apartment status (VACANT, OCCUPIED, MAINTENANCE, RESERVED)")
            @PathVariable Apartment.ApartmentStatus status) {
        List<ApartmentDto> apartments = apartmentService.getApartmentsByStatus(status);
        return ResponseEntity.ok(apartments);
    }

    @Operation(summary = "Get apartments by building", description = "Retrieve apartments filtered by building ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<ApartmentDto>> getApartmentsByBuilding(
            @Parameter(description = "Building ID") @PathVariable Long buildingId) {
        List<ApartmentDto> apartments = apartmentService.getApartmentsByBuildingId(buildingId);
        return ResponseEntity.ok(apartments);
    }

    @Operation(summary = "Update apartment", description = "Update an existing apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Apartment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Apartment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long id,
            @Valid @RequestBody ApartmentRequest request) {
        ApartmentDto dto = mapToDto(request);
        apartmentService.updateApartment(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete apartment", description = "Delete an apartment from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Apartment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Apartment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }

    private ApartmentDto mapToDto(ApartmentRequest request) {
        ApartmentDto dto = new ApartmentDto();
        dto.setUnitNumber(request.getUnitNumber());
        dto.setBuildingId(request.getBuildingId());
        dto.setFloor(request.getFloor());
        dto.setBedrooms(request.getBedrooms());
        dto.setBathrooms(request.getBathrooms());
        dto.setSquareFootage(request.getSquareFootage());
        dto.setMonthlyRent(request.getMonthlyRent());
        dto.setStatus(request.getStatus());
        dto.setDescription(request.getDescription());
        return dto;
    }
}
