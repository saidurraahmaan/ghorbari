package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.LeaseDto;
import com.s4r.ghorbari.core.entity.Lease;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ILeaseService {

    void createLease(LeaseDto dto);

    List<LeaseDto> getAllLeases();

    Optional<LeaseDto> getLeaseById(Long id);

    List<LeaseDto> getLeasesByStatus(Lease.LeaseStatus status);

    List<LeaseDto> getLeasesByApartmentId(Long apartmentId);

    List<LeaseDto> getLeasesByResidentId(Long residentId);

    List<LeaseDto> getExpiringLeases(LocalDate beforeDate);

    Optional<LeaseDto> getActiveLeaseByApartment(Long apartmentId);

    void updateLease(Long id, LeaseDto dto);

    void terminateLease(Long id, LocalDate terminationDate, String reason);

    void renewLease(Long id, LocalDate newEndDate);

    void deleteLease(Long id);
}
