package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.BuildingDto;
import com.s4r.ghorbari.core.entity.Building;
import com.s4r.ghorbari.core.service.IBuildingService;
import com.s4r.ghorbari.web.dto.BuildingRequest;
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

@Tag(name = "Buildings", description = "Building management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    private final IBuildingService buildingService;

    public BuildingController(IBuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Operation(summary = "Create new building", description = "Add a new building to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Building created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createBuilding(@Valid @RequestBody BuildingRequest request) {
        BuildingDto dto = mapToDto(request);
        buildingService.createBuilding(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all buildings", description = "Retrieve all buildings for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buildings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<BuildingDto>> getAllBuildings() {
        List<BuildingDto> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    @Operation(summary = "Get building by ID", description = "Retrieve a specific building by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building found",
                    content = @Content(schema = @Schema(implementation = BuildingDto.class))),
            @ApiResponse(responseCode = "404", description = "Building not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BuildingDto> getBuildingById(
            @Parameter(description = "Building ID") @PathVariable Long id) {
        return buildingService.getBuildingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get buildings by status", description = "Retrieve buildings filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buildings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BuildingDto>> getBuildingsByStatus(
            @Parameter(description = "Building status (ACTIVE, UNDER_CONSTRUCTION, MAINTENANCE, INACTIVE)")
            @PathVariable Building.BuildingStatus status) {
        List<BuildingDto> buildings = buildingService.getBuildingsByStatus(status);
        return ResponseEntity.ok(buildings);
    }

    @Operation(summary = "Update building", description = "Update an existing building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Building updated successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBuilding(
            @Parameter(description = "Building ID") @PathVariable Long id,
            @Valid @RequestBody BuildingRequest request) {
        BuildingDto dto = mapToDto(request);
        buildingService.updateBuilding(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete building", description = "Delete a building from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Building deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuilding(
            @Parameter(description = "Building ID") @PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign manager to building", description = "Assign a manager (user with ROLE_MANAGER) to a building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Manager assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Building or manager not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "User does not have ROLE_MANAGER",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{buildingId}/managers/{managerId}")
    public ResponseEntity<?> assignManager(
            @Parameter(description = "Building ID") @PathVariable Long buildingId,
            @Parameter(description = "Manager User ID") @PathVariable Long managerId) {
        buildingService.assignManagerToBuilding(buildingId, managerId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove manager from building", description = "Remove a manager from a building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Manager removed successfully"),
            @ApiResponse(responseCode = "404", description = "Building or manager not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{buildingId}/managers/{managerId}")
    public ResponseEntity<?> removeManager(
            @Parameter(description = "Building ID") @PathVariable Long buildingId,
            @Parameter(description = "Manager User ID") @PathVariable Long managerId) {
        buildingService.removeManagerFromBuilding(buildingId, managerId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get buildings by manager", description = "Retrieve all buildings managed by a specific manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buildings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/managers/{managerId}")
    public ResponseEntity<List<BuildingDto>> getBuildingsByManager(
            @Parameter(description = "Manager User ID") @PathVariable Long managerId) {
        List<BuildingDto> buildings = buildingService.getBuildingsByManager(managerId);
        return ResponseEntity.ok(buildings);
    }

    private BuildingDto mapToDto(BuildingRequest request) {
        BuildingDto dto = new BuildingDto();
        dto.setName(request.getName());
        dto.setAddress(request.getAddress());
        dto.setTotalFloors(request.getTotalFloors());
        dto.setTotalUnits(request.getTotalUnits());
        dto.setYearBuilt(request.getYearBuilt());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus());
        return dto;
    }
}
