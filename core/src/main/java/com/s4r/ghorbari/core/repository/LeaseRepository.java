package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

    List<Lease> findByStatus(Lease.LeaseStatus status);

    List<Lease> findByApartmentId(Long apartmentId);

    List<Lease> findByPrimaryResidentId(Long residentId);

    Optional<Lease> findByApartmentIdAndStatus(Long apartmentId, Lease.LeaseStatus status);

    @Query("SELECT l FROM Lease l WHERE l.endDate BETWEEN :startDate AND :endDate")
    List<Lease> findExpiringLeases(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT l FROM Lease l WHERE l.status = 'ACTIVE' AND l.apartmentId = :apartmentId")
    Optional<Lease> findActiveLeaseByApartment(@Param("apartmentId") Long apartmentId);
}
