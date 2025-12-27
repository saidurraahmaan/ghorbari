package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.AmenityBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AmenityBookingRepository extends JpaRepository<AmenityBooking, Long> {

    Optional<AmenityBooking> findByBookingReference(String bookingReference);

    List<AmenityBooking> findByAmenityId(Long amenityId);

    List<AmenityBooking> findByResidentId(Long residentId);

    List<AmenityBooking> findByStatus(AmenityBooking.BookingStatus status);

    @Query("SELECT ab FROM AmenityBooking ab WHERE ab.amenityId = :amenityId AND ab.startTime <= :endTime AND ab.endTime >= :startTime AND ab.status NOT IN ('CANCELLED', 'REJECTED')")
    List<AmenityBooking> findConflictingBookings(@Param("amenityId") Long amenityId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

    @Query("SELECT ab FROM AmenityBooking ab WHERE ab.amenityId = :amenityId AND ab.startTime >= :startDate AND ab.endTime <= :endDate")
    List<AmenityBooking> findBookingsByAmenityAndDateRange(@Param("amenityId") Long amenityId,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ab FROM AmenityBooking ab WHERE ab.residentId = :residentId AND ab.startTime >= :startDate AND ab.endTime <= :endDate")
    List<AmenityBooking> findBookingsByResidentAndDateRange(@Param("residentId") Long residentId,
                                                             @Param("startDate") LocalDateTime startDate,
                                                             @Param("endDate") LocalDateTime endDate);
}
