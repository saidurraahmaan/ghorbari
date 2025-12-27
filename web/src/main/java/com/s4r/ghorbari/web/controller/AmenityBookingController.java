package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.domain.AmenityBookingDto;
import com.s4r.ghorbari.core.entity.AmenityBooking;
import com.s4r.ghorbari.core.service.IAmenityBookingService;
import com.s4r.ghorbari.web.dto.AmenityBookingRequest;
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

@Tag(name = "Amenity Bookings", description = "Amenity booking management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/amenity-bookings")
public class AmenityBookingController {

    private final IAmenityBookingService amenityBookingService;

    public AmenityBookingController(IAmenityBookingService amenityBookingService) {
        this.amenityBookingService = amenityBookingService;
    }

    @Operation(summary = "Create booking", description = "Create a new amenity booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or amenity not available",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody AmenityBookingRequest request) {
        AmenityBookingDto dto = mapToDto(request);
        amenityBookingService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all bookings", description = "Retrieve all amenity bookings for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<AmenityBookingDto>> getAllBookings() {
        List<AmenityBookingDto> bookings = amenityBookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Get booking by ID", description = "Retrieve a specific booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found",
                    content = @Content(schema = @Schema(implementation = AmenityBookingDto.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AmenityBookingDto> getBookingById(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        return amenityBookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get booking by reference", description = "Retrieve a booking by its reference number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found",
                    content = @Content(schema = @Schema(implementation = AmenityBookingDto.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/reference/{bookingReference}")
    public ResponseEntity<AmenityBookingDto> getBookingByReference(
            @Parameter(description = "Booking reference") @PathVariable String bookingReference) {
        return amenityBookingService.getBookingByReference(bookingReference)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get bookings by amenity", description = "Retrieve all bookings for a specific amenity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/amenity/{amenityId}")
    public ResponseEntity<List<AmenityBookingDto>> getBookingsByAmenity(
            @Parameter(description = "Amenity ID") @PathVariable Long amenityId) {
        List<AmenityBookingDto> bookings = amenityBookingService.getBookingsByAmenityId(amenityId);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Get bookings by resident", description = "Retrieve all bookings for a specific resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<AmenityBookingDto>> getBookingsByResident(
            @Parameter(description = "Resident ID") @PathVariable Long residentId) {
        List<AmenityBookingDto> bookings = amenityBookingService.getBookingsByResidentId(residentId);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Get bookings by status", description = "Retrieve bookings filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AmenityBookingDto>> getBookingsByStatus(
            @Parameter(description = "Booking status (PENDING, APPROVED, CONFIRMED, etc.)")
            @PathVariable AmenityBooking.BookingStatus status) {
        List<AmenityBookingDto> bookings = amenityBookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Get bookings by amenity and date range", description = "Retrieve bookings for an amenity within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/amenity/{amenityId}/date-range")
    public ResponseEntity<List<AmenityBookingDto>> getBookingsByAmenityAndDateRange(
            @Parameter(description = "Amenity ID") @PathVariable Long amenityId,
            @Parameter(description = "Start date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AmenityBookingDto> bookings = amenityBookingService.getBookingsByAmenityAndDateRange(amenityId, startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Get bookings by resident and date range", description = "Retrieve bookings for a resident within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/resident/{residentId}/date-range")
    public ResponseEntity<List<AmenityBookingDto>> getBookingsByResidentAndDateRange(
            @Parameter(description = "Resident ID") @PathVariable Long residentId,
            @Parameter(description = "Start date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AmenityBookingDto> bookings = amenityBookingService.getBookingsByResidentAndDateRange(residentId, startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Check availability", description = "Check if an amenity is available for a time slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Availability checked"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/amenity/{amenityId}/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @Parameter(description = "Amenity ID") @PathVariable Long amenityId,
            @Parameter(description = "Start time (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "End time (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        boolean available = amenityBookingService.isAvailable(amenityId, startTime, endTime);
        return ResponseEntity.ok(available);
    }

    @Operation(summary = "Update booking", description = "Update an existing booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id,
            @Valid @RequestBody AmenityBookingRequest request) {
        AmenityBookingDto dto = mapToDto(request);
        amenityBookingService.updateBooking(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Approve booking", description = "Approve a pending booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking approved successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id,
            @Parameter(description = "Approver user ID") @RequestParam Long approvedById) {
        amenityBookingService.approveBooking(id, approvedById);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reject booking", description = "Reject a booking with a reason")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking rejected successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id,
            @Parameter(description = "Rejection reason") @RequestParam String reason) {
        amenityBookingService.rejectBooking(id, reason);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Confirm booking", description = "Confirm an approved booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking confirmed successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/confirm")
    public ResponseEntity<?> confirmBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        amenityBookingService.confirmBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel booking", description = "Cancel a booking with a reason")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id,
            @Parameter(description = "Cancellation reason") @RequestParam(required = false) String reason) {
        amenityBookingService.cancelBooking(id, reason);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark as completed", description = "Mark a booking as completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking marked as completed"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> markAsCompleted(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        amenityBookingService.markAsCompleted(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark as no-show", description = "Mark a booking as no-show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking marked as no-show"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/no-show")
    public ResponseEntity<?> markAsNoShow(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        amenityBookingService.markAsNoShow(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete booking", description = "Delete a booking from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        amenityBookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    private AmenityBookingDto mapToDto(AmenityBookingRequest request) {
        AmenityBookingDto dto = new AmenityBookingDto();
        dto.setBookingReference(request.getBookingReference());
        dto.setAmenityId(request.getAmenityId());
        dto.setResidentId(request.getResidentId());
        dto.setStartTime(request.getStartTime());
        dto.setEndTime(request.getEndTime());
        dto.setStatus(request.getStatus());
        dto.setNumberOfGuests(request.getNumberOfGuests());
        dto.setPurpose(request.getPurpose());
        dto.setSpecialRequests(request.getSpecialRequests());
        dto.setNotes(request.getNotes());
        return dto;
    }
}
