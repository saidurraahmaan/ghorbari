package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {

    Optional<MaintenanceRequest> findByRequestNumber(String requestNumber);

    List<MaintenanceRequest> findByStatus(MaintenanceRequest.RequestStatus status);

    List<MaintenanceRequest> findByPriority(MaintenanceRequest.Priority priority);

    List<MaintenanceRequest> findByCategory(MaintenanceRequest.RequestCategory category);

    List<MaintenanceRequest> findByApartmentId(Long apartmentId);

    List<MaintenanceRequest> findByResidentId(Long residentId);

    List<MaintenanceRequest> findByAssignedToId(Long assignedToId);

    @Query("SELECT mr FROM MaintenanceRequest mr WHERE mr.status IN ('OPEN', 'ASSIGNED', 'IN_PROGRESS')")
    List<MaintenanceRequest> findActiveRequests();

    @Query("SELECT mr FROM MaintenanceRequest mr WHERE mr.requestedAt BETWEEN :startDate AND :endDate")
    List<MaintenanceRequest> findRequestsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);
}
