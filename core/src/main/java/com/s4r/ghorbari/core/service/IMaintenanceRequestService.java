package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.entity.MaintenanceRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMaintenanceRequestService {

    void createRequest(MaintenanceRequest request);

    List<MaintenanceRequest> getAllRequests();

    Optional<MaintenanceRequest> getRequestById(Long id);

    Optional<MaintenanceRequest> getRequestByNumber(String requestNumber);

    List<MaintenanceRequest> getRequestsByStatus(MaintenanceRequest.RequestStatus status);

    List<MaintenanceRequest> getRequestsByPriority(MaintenanceRequest.Priority priority);

    List<MaintenanceRequest> getRequestsByCategory(MaintenanceRequest.RequestCategory category);

    List<MaintenanceRequest> getRequestsByApartmentId(Long apartmentId);

    List<MaintenanceRequest> getRequestsByResidentId(Long residentId);

    List<MaintenanceRequest> getRequestsByAssignedTo(Long assignedToId);

    List<MaintenanceRequest> getActiveRequests();

    List<MaintenanceRequest> getRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    void updateRequest(MaintenanceRequest request);

    void assignToStaff(Long requestId, Long staffId);

    void updateStatus(Long requestId, MaintenanceRequest.RequestStatus status);

    void scheduleRequest(Long requestId, LocalDateTime scheduledDate);

    void completeRequest(Long requestId, String resolutionNotes);

    void cancelRequest(Long requestId, String reason);

    void deleteRequest(Long id);
}
