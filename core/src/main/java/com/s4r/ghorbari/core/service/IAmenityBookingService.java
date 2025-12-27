package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.AmenityBookingDto;
import com.s4r.ghorbari.core.entity.AmenityBooking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAmenityBookingService {

    void createBooking(AmenityBookingDto dto);

    List<AmenityBookingDto> getAllBookings();

    Optional<AmenityBookingDto> getBookingById(Long id);

    Optional<AmenityBookingDto> getBookingByReference(String bookingReference);

    List<AmenityBookingDto> getBookingsByAmenityId(Long amenityId);

    List<AmenityBookingDto> getBookingsByResidentId(Long residentId);

    List<AmenityBookingDto> getBookingsByStatus(AmenityBooking.BookingStatus status);

    List<AmenityBookingDto> getBookingsByAmenityAndDateRange(Long amenityId, LocalDateTime startDate, LocalDateTime endDate);

    List<AmenityBookingDto> getBookingsByResidentAndDateRange(Long residentId, LocalDateTime startDate, LocalDateTime endDate);

    boolean isAvailable(Long amenityId, LocalDateTime startTime, LocalDateTime endTime);

    void updateBooking(Long id, AmenityBookingDto dto);

    void approveBooking(Long id, Long approvedById);

    void rejectBooking(Long id, String reason);

    void confirmBooking(Long id);

    void cancelBooking(Long id, String reason);

    void markAsCompleted(Long id);

    void markAsNoShow(Long id);

    void deleteBooking(Long id);
}
