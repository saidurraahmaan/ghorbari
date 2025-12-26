package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.entity.Apartment;
import com.s4r.ghorbari.core.service.IApartmentService;
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
            @ApiResponse(responseCode = "201", description = "Apartment created successfully",
                    content = @Content(schema = @Schema(implementation = Apartment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        Apartment created = apartmentService.createApartment(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get all apartments", description = "Retrieve all apartments for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<Apartment>> getAllApartments() {
        List<Apartment> apartments = apartmentService.getAllApartments();
        return ResponseEntity.ok(apartments);
    }

    @Operation(summary = "Get apartment by ID", description = "Retrieve a specific apartment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment found",
                    content = @Content(schema = @Schema(implementation = Apartment.class))),
            @ApiResponse(responseCode = "404", description = "Apartment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getApartmentById(
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
    public ResponseEntity<List<Apartment>> getApartmentsByStatus(
            @Parameter(description = "Apartment status (VACANT, OCCUPIED, MAINTENANCE, RESERVED)")
            @PathVariable Apartment.ApartmentStatus status) {
        List<Apartment> apartments = apartmentService.getApartmentsByStatus(status);
        return ResponseEntity.ok(apartments);
    }

    @Operation(summary = "Update apartment", description = "Update an existing apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment updated successfully",
                    content = @Content(schema = @Schema(implementation = Apartment.class))),
            @ApiResponse(responseCode = "404", description = "Apartment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Apartment> updateApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long id,
            @RequestBody Apartment apartment) {
        apartment.setId(id);
        Apartment updated = apartmentService.updateApartment(apartment);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete apartment", description = "Delete an apartment from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Apartment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Apartment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(
            @Parameter(description = "Apartment ID") @PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }
}
