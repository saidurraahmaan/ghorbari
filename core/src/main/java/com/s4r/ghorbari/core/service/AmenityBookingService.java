package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.AmenityBookingDto;
import com.s4r.ghorbari.core.entity.AmenityBooking;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.AmenityBookingRepository;
import com.s4r.ghorbari.core.entity.Role.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmenityBookingService implements IAmenityBookingService {

    private final AmenityBookingRepository amenityBookingRepository;

    public AmenityBookingService(AmenityBookingRepository amenityBookingRepository) {
        this.amenityBookingRepository = amenityBookingRepository;
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_RESIDENT})
    public void createBooking(AmenityBookingDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        // Check availability
        if (!isAvailable(dto.getAmenityId(), dto.getStartTime(), dto.getEndTime())) {
            throw new ServiceException(ErrorCode.INVALID_OPERATION, "The amenity is not available for the selected time slot");
        }

        AmenityBooking booking = dto.toEntity();

        // Generate booking reference if not provided
        if (booking.getBookingReference() == null || booking.getBookingReference().isEmpty()) {
            booking.setBookingReference(generateBookingReference());
        }

        booking.setTenantId(tenantId);
        amenityBookingRepository.save(booking);
    }

    @Override
    public List<AmenityBookingDto> getAllBookings() {
        return amenityBookingRepository.findAll().stream()
                .map(AmenityBookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AmenityBookingDto> getBookingById(Long id) {
        return amenityBookingRepository.findById(id)
                .map(AmenityBookingDto::new);
    }

    @Override
    public Optional<AmenityBookingDto> getBookingByReference(String bookingReference) {
        return amenityBookingRepository.findByBookingReference(bookingReference)
                .map(AmenityBookingDto::new);
    }

    @Override
    public List<AmenityBookingDto> getBookingsByAmenityId(Long amenityId) {
        return amenityBookingRepository.findByAmenityId(amenityId).stream()
                .map(AmenityBookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityBookingDto> getBookingsByResidentId(Long residentId) {
        return amenityBookingRepository.findByResidentId(residentId).stream()
                .map(AmenityBookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityBookingDto> getBookingsByStatus(AmenityBooking.BookingStatus status) {
        return amenityBookingRepository.findByStatus(status).stream()
                .map(AmenityBookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityBookingDto> getBookingsByAmenityAndDateRange(Long amenityId, LocalDateTime startDate, LocalDateTime endDate) {
        return amenityBookingRepository.findBookingsByAmenityAndDateRange(amenityId, startDate, endDate).stream()
                .map(AmenityBookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityBookingDto> getBookingsByResidentAndDateRange(Long residentId, LocalDateTime startDate, LocalDateTime endDate) {
        return amenityBookingRepository.findBookingsByResidentAndDateRange(residentId, startDate, endDate).stream()
                .map(AmenityBookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAvailable(Long amenityId, LocalDateTime startTime, LocalDateTime endTime) {
        List<AmenityBooking> conflicts = amenityBookingRepository.findConflictingBookings(amenityId, startTime, endTime);
        return conflicts.isEmpty();
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateBooking(Long id, AmenityBookingDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        AmenityBooking booking = dto.toEntity();
        booking.setId(id);
        booking.setTenantId(tenantId);
        amenityBookingRepository.save(booking);
    }

    @Override
    public void approveBooking(Long id, Long approvedById) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        booking.setStatus(AmenityBooking.BookingStatus.APPROVED);
        booking.setApprovedById(approvedById);
        booking.setApprovedAt(LocalDateTime.now());
        amenityBookingRepository.save(booking);
    }

    @Override
    public void rejectBooking(Long id, String reason) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        booking.setStatus(AmenityBooking.BookingStatus.REJECTED);
        booking.setRejectionReason(reason);
        amenityBookingRepository.save(booking);
    }

    @Override
    public void confirmBooking(Long id) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        booking.setStatus(AmenityBooking.BookingStatus.CONFIRMED);
        amenityBookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long id, String reason) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        booking.setStatus(AmenityBooking.BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancellationReason(reason);
        amenityBookingRepository.save(booking);
    }

    @Override
    public void markAsCompleted(Long id) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        booking.setStatus(AmenityBooking.BookingStatus.COMPLETED);
        amenityBookingRepository.save(booking);
    }

    @Override
    public void markAsNoShow(Long id) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        booking.setStatus(AmenityBooking.BookingStatus.NO_SHOW);
        amenityBookingRepository.save(booking);
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void deleteBooking(Long id) {
        AmenityBooking booking = amenityBookingRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found"));

        amenityBookingRepository.delete(booking);
    }

    private String generateBookingReference() {
        return "BKG-" + LocalDateTime.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
