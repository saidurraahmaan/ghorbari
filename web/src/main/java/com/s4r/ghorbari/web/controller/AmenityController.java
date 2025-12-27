package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.AmenityDto;
import com.s4r.ghorbari.core.entity.Amenity;
import com.s4r.ghorbari.core.service.IAmenityService;
import com.s4r.ghorbari.web.dto.AmenityRequest;
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

@Tag(name = "Amenities", description = "Building amenity management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final IAmenityService amenityService;

    public AmenityController(IAmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @Operation(summary = "Create amenity", description = "Create a new amenity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Amenity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createAmenity(@Valid @RequestBody AmenityRequest request) {
        AmenityDto dto = mapToDto(request);
        amenityService.createAmenity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all amenities", description = "Retrieve all amenities for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<AmenityDto>> getAllAmenities() {
        List<AmenityDto> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }

    @Operation(summary = "Get amenity by ID", description = "Retrieve a specific amenity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenity found",
                    content = @Content(schema = @Schema(implementation = AmenityDto.class))),
            @ApiResponse(responseCode = "404", description = "Amenity not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AmenityDto> getAmenityById(
            @Parameter(description = "Amenity ID") @PathVariable Long id) {
        return amenityService.getAmenityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get amenities by type", description = "Retrieve amenities filtered by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<AmenityDto>> getAmenitiesByType(
            @Parameter(description = "Amenity type (GYM, SWIMMING_POOL, PARKING, etc.)")
            @PathVariable Amenity.AmenityType type) {
        List<AmenityDto> amenities = amenityService.getAmenitiesByType(type);
        return ResponseEntity.ok(amenities);
    }

    @Operation(summary = "Get amenities by status", description = "Retrieve amenities filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AmenityDto>> getAmenitiesByStatus(
            @Parameter(description = "Amenity status (AVAILABLE, UNDER_MAINTENANCE, etc.)")
            @PathVariable Amenity.AmenityStatus status) {
        List<AmenityDto> amenities = amenityService.getAmenitiesByStatus(status);
        return ResponseEntity.ok(amenities);
    }

    @Operation(summary = "Get bookable amenities", description = "Retrieve all amenities that can be booked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/bookable")
    public ResponseEntity<List<AmenityDto>> getBookableAmenities() {
        List<AmenityDto> amenities = amenityService.getBookableAmenities();
        return ResponseEntity.ok(amenities);
    }

    @Operation(summary = "Get available bookable amenities", description = "Retrieve amenities that are both available and bookable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/available-bookable")
    public ResponseEntity<List<AmenityDto>> getAvailableBookableAmenities() {
        List<AmenityDto> amenities = amenityService.getAvailableBookableAmenities();
        return ResponseEntity.ok(amenities);
    }

    @Operation(summary = "Update amenity", description = "Update an existing amenity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Amenity updated successfully"),
            @ApiResponse(responseCode = "404", description = "Amenity not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAmenity(
            @Parameter(description = "Amenity ID") @PathVariable Long id,
            @Valid @RequestBody AmenityRequest request) {
        AmenityDto dto = mapToDto(request);
        amenityService.updateAmenity(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete amenity", description = "Delete an amenity from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Amenity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Amenity not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAmenity(
            @Parameter(description = "Amenity ID") @PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }

    private AmenityDto mapToDto(AmenityRequest request) {
        AmenityDto dto = new AmenityDto();
        dto.setName(request.getName());
        dto.setDescription(request.getDescription());
        dto.setType(request.getType());
        dto.setLocation(request.getLocation());
        dto.setMaxCapacity(request.getMaxCapacity());
        dto.setBookingDurationMinutes(request.getBookingDurationMinutes());
        dto.setAdvanceBookingDays(request.getAdvanceBookingDays());
        dto.setIsBookable(request.getIsBookable());
        dto.setRequiresApproval(request.getRequiresApproval());
        dto.setStatus(request.getStatus());
        dto.setOperatingHours(request.getOperatingHours());
        dto.setRules(request.getRules());
        dto.setNotes(request.getNotes());
        return dto;
    }
}
