package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.entity.MaintenanceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MaintenanceRequestRequest {

    private String requestNumber;

    private Long apartmentId;

    private Long residentId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Category is required")
    private MaintenanceRequest.RequestCategory category;

    private MaintenanceRequest.Priority priority;

    private MaintenanceRequest.RequestStatus status;

    private Long assignedToId;

    private LocalDateTime scheduledDate;

    private String notes;

    // Constructors
    public MaintenanceRequestRequest() {
    }

    // Getters and Setters
    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MaintenanceRequest.RequestCategory getCategory() {
        return category;
    }

    public void setCategory(MaintenanceRequest.RequestCategory category) {
        this.category = category;
    }

    public MaintenanceRequest.Priority getPriority() {
        return priority;
    }

    public void setPriority(MaintenanceRequest.Priority priority) {
        this.priority = priority;
    }

    public MaintenanceRequest.RequestStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceRequest.RequestStatus status) {
        this.status = status;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
