package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.entity.Apartment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ApartmentRequest {

    @NotBlank(message = "Apartment number is required")
    private String unitNumber;

    @NotNull(message = "Building ID is required")
    private Long buildingId;

    @Positive(message = "Floor must be positive")
    private Integer floor;

    @Positive(message = "Number of bedrooms must be positive")
    private Integer bedrooms;

    @Positive(message = "Number of bathrooms must be positive")
    private Integer bathrooms;

    private BigDecimal squareFootage;

    private BigDecimal rentAmount;

    private Apartment.ApartmentStatus status;

    private String description;

    // Constructors
    public ApartmentRequest() {
    }

    // Getters and Setters
    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public BigDecimal getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(BigDecimal squareFootage) {
        this.squareFootage = squareFootage;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Apartment.ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(Apartment.ApartmentStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
