package com.s4r.ghorbari.web.dto;

import jakarta.validation.constraints.NotNull;

public class ResidentRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Apartment ID is required")
    private Long apartmentId;

    @NotNull(message = "Is primary resident flag is required")
    private Boolean isPrimaryResident;

    // Constructors
    public ResidentRequest() {
    }

    public ResidentRequest(Long userId, Long apartmentId, Boolean isPrimaryResident) {
        this.userId = userId;
        this.apartmentId = apartmentId;
        this.isPrimaryResident = isPrimaryResident;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Boolean getIsPrimaryResident() {
        return isPrimaryResident;
    }

    public void setIsPrimaryResident(Boolean isPrimaryResident) {
        this.isPrimaryResident = isPrimaryResident;
    }
}
