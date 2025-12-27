package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.AnnouncementDto;
import com.s4r.ghorbari.core.entity.Announcement;
import com.s4r.ghorbari.core.service.IAnnouncementService;
import com.s4r.ghorbari.web.dto.AnnouncementRequest;
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

@Tag(name = "Announcements", description = "Announcement management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final IAnnouncementService announcementService;

    public AnnouncementController(IAnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Operation(summary = "Create announcement", description = "Create a new announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Announcement created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createAnnouncement(@Valid @RequestBody AnnouncementRequest request) {
        AnnouncementDto dto = mapToDto(request);
        announcementService.createAnnouncement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all announcements", description = "Retrieve all announcements for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<AnnouncementDto>> getAllAnnouncements() {
        List<AnnouncementDto> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Get announcement by ID", description = "Retrieve a specific announcement by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement found",
                    content = @Content(schema = @Schema(implementation = AnnouncementDto.class))),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(
            @Parameter(description = "Announcement ID") @PathVariable Long id) {
        return announcementService.getAnnouncementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get announcements by type", description = "Retrieve announcements filtered by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByType(
            @Parameter(description = "Announcement type (GENERAL, MAINTENANCE, EVENT, etc.)")
            @PathVariable Announcement.AnnouncementType type) {
        List<AnnouncementDto> announcements = announcementService.getAnnouncementsByType(type);
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Get announcements by priority", description = "Retrieve announcements filtered by priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByPriority(
            @Parameter(description = "Priority (LOW, NORMAL, HIGH, URGENT)")
            @PathVariable Announcement.Priority priority) {
        List<AnnouncementDto> announcements = announcementService.getAnnouncementsByPriority(priority);
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Get published announcements", description = "Retrieve all published announcements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/published")
    public ResponseEntity<List<AnnouncementDto>> getPublishedAnnouncements() {
        List<AnnouncementDto> announcements = announcementService.getPublishedAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Get pinned announcements", description = "Retrieve all pinned announcements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/pinned")
    public ResponseEntity<List<AnnouncementDto>> getPinnedAnnouncements() {
        List<AnnouncementDto> announcements = announcementService.getPinnedAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Get active announcements", description = "Retrieve active announcements (published and not expired)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/active")
    public ResponseEntity<List<AnnouncementDto>> getActiveAnnouncements() {
        List<AnnouncementDto> announcements = announcementService.getActiveAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Get announcements by date range", description = "Retrieve announcements within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AnnouncementDto> announcements = announcementService.getAnnouncementsByDateRange(startDate, endDate);
        return ResponseEntity.ok(announcements);
    }

    @Operation(summary = "Update announcement", description = "Update an existing announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Announcement updated successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnouncement(
            @Parameter(description = "Announcement ID") @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequest request) {
        AnnouncementDto dto = mapToDto(request);
        announcementService.updateAnnouncement(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publish announcement", description = "Publish an announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Announcement published successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/publish")
    public ResponseEntity<?> publishAnnouncement(
            @Parameter(description = "Announcement ID") @PathVariable Long id) {
        announcementService.publishAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Unpublish announcement", description = "Unpublish an announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Announcement unpublished successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/unpublish")
    public ResponseEntity<?> unpublishAnnouncement(
            @Parameter(description = "Announcement ID") @PathVariable Long id) {
        announcementService.unpublishAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pin announcement", description = "Pin an announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Announcement pinned successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/pin")
    public ResponseEntity<?> pinAnnouncement(
            @Parameter(description = "Announcement ID") @PathVariable Long id) {
        announcementService.pinAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Unpin announcement", description = "Unpin an announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Announcement unpinned successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/unpin")
    public ResponseEntity<?> unpinAnnouncement(
            @Parameter(description = "Announcement ID") @PathVariable Long id) {
        announcementService.unpinAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete announcement", description = "Delete an announcement from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Announcement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnouncement(
            @Parameter(description = "Announcement ID") @PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    private AnnouncementDto mapToDto(AnnouncementRequest request) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setTitle(request.getTitle());
        dto.setContent(request.getContent());
        dto.setType(request.getType());
        dto.setPriority(request.getPriority());
        dto.setExpiresAt(request.getExpiresAt());
        dto.setIsPublished(request.getIsPublished());
        dto.setIsPinned(request.getIsPinned());
        dto.setCreatedById(request.getCreatedById());
        dto.setTargetAudience(request.getTargetAudience());
        dto.setAttachmentUrl(request.getAttachmentUrl());
        return dto;
    }
}
