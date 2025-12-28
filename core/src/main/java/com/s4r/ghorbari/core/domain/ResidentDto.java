package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Resident;

public class ResidentDto {

    private Long id;
    private Long userId;
    private Long apartmentId;
    private Boolean isPrimaryResident;

    // Constructors
    public ResidentDto() {
    }

    public ResidentDto(Resident resident) {
        this.id = resident.getId();
        this.userId = resident.getUserId();
        this.apartmentId = resident.getApartmentId();
        this.isPrimaryResident = resident.getIsPrimaryResident();
    }

    public Resident toEntity() {
        Resident resident = new Resident();
        resident.setId(this.id);
        resident.setUserId(this.userId);
        resident.setApartmentId(this.apartmentId);
        resident.setIsPrimaryResident(this.isPrimaryResident);
        return resident;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
