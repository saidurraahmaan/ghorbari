package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.entity.MaintenanceRequest;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.MaintenanceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MaintenanceRequestService implements IMaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;

    public MaintenanceRequestService(MaintenanceRequestRepository maintenanceRequestRepository) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
    }

    @Override
    public void createRequest(MaintenanceRequest request) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        // Generate request number if not provided
        if (request.getRequestNumber() == null || request.getRequestNumber().isEmpty()) {
            request.setRequestNumber(generateRequestNumber());
        }

        request.setTenantId(tenantId);
        maintenanceRequestRepository.save(request);
    }

    @Override
    public List<MaintenanceRequest> getAllRequests() {
        return maintenanceRequestRepository.findAll();
    }

    @Override
    public Optional<MaintenanceRequest> getRequestById(Long id) {
        return maintenanceRequestRepository.findById(id);
    }

    @Override
    public Optional<MaintenanceRequest> getRequestByNumber(String requestNumber) {
        return maintenanceRequestRepository.findByRequestNumber(requestNumber);
    }

    @Override
    public List<MaintenanceRequest> getRequestsByStatus(MaintenanceRequest.RequestStatus status) {
        return maintenanceRequestRepository.findByStatus(status);
    }

    @Override
    public List<MaintenanceRequest> getRequestsByPriority(MaintenanceRequest.Priority priority) {
        return maintenanceRequestRepository.findByPriority(priority);
    }

    @Override
    public List<MaintenanceRequest> getRequestsByCategory(MaintenanceRequest.RequestCategory category) {
        return maintenanceRequestRepository.findByCategory(category);
    }

    @Override
    public List<MaintenanceRequest> getRequestsByApartmentId(Long apartmentId) {
        return maintenanceRequestRepository.findByApartmentId(apartmentId);
    }

    @Override
    public List<MaintenanceRequest> getRequestsByResidentId(Long residentId) {
        return maintenanceRequestRepository.findByResidentId(residentId);
    }

    @Override
    public List<MaintenanceRequest> getRequestsByAssignedTo(Long assignedToId) {
        return maintenanceRequestRepository.findByAssignedToId(assignedToId);
    }

    @Override
    public List<MaintenanceRequest> getActiveRequests() {
        return maintenanceRequestRepository.findActiveRequests();
    }

    @Override
    public List<MaintenanceRequest> getRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return maintenanceRequestRepository.findRequestsByDateRange(startDate, endDate);
    }

    @Override
    public void updateRequest(MaintenanceRequest request) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        maintenanceRequestRepository.findById(request.getId())
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        request.setTenantId(tenantId);
        maintenanceRequestRepository.save(request);
    }

    @Override
    public void assignToStaff(Long requestId, Long staffId) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        request.setAssignedToId(staffId);
        request.setStatus(MaintenanceRequest.RequestStatus.ASSIGNED);
        maintenanceRequestRepository.save(request);
    }

    @Override
    public void updateStatus(Long requestId, MaintenanceRequest.RequestStatus status) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        request.setStatus(status);
        maintenanceRequestRepository.save(request);
    }

    @Override
    public void scheduleRequest(Long requestId, LocalDateTime scheduledDate) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        request.setScheduledDate(scheduledDate);
        if (request.getStatus() == MaintenanceRequest.RequestStatus.OPEN) {
            request.setStatus(MaintenanceRequest.RequestStatus.ASSIGNED);
        }
        maintenanceRequestRepository.save(request);
    }

    @Override
    public void completeRequest(Long requestId, String resolutionNotes) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        request.setStatus(MaintenanceRequest.RequestStatus.COMPLETED);
        request.setCompletedAt(LocalDateTime.now());
        request.setResolutionNotes(resolutionNotes);
        maintenanceRequestRepository.save(request);
    }

    @Override
    public void cancelRequest(Long requestId, String reason) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        request.setStatus(MaintenanceRequest.RequestStatus.CANCELLED);
        request.setResolutionNotes(reason);
        maintenanceRequestRepository.save(request);
    }

    @Override
    public void deleteRequest(Long id) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Maintenance request not found"));

        maintenanceRequestRepository.delete(request);
    }

    private String generateRequestNumber() {
        return "REQ-" + LocalDateTime.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
