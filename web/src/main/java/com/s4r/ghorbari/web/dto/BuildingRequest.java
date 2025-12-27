package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.entity.Building;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class BuildingRequest {

    @NotBlank(message = "Building name is required")
    private String name;

    private String address;

    @Positive(message = "Total floors must be positive")
    private Integer totalFloors;

    @Positive(message = "Total units must be positive")
    private Integer totalUnits;

    @Positive(message = "Year built must be positive")
    private Integer yearBuilt;

    private String description;

    private Building.BuildingStatus status;

    // Constructors
    public BuildingRequest() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(Integer totalFloors) {
        this.totalFloors = totalFloors;
    }

    public Integer getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(Integer totalUnits) {
        this.totalUnits = totalUnits;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Building.BuildingStatus getStatus() {
        return status;
    }

    public void setStatus(Building.BuildingStatus status) {
        this.status = status;
    }
}
