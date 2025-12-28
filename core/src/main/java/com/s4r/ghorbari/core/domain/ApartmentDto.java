package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Apartment;

import java.math.BigDecimal;

public class ApartmentDto {

    private Long id;
    private String unitNumber;
    private Long buildingId;
    private String buildingName;
    private Integer floor;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal squareFootage;
    private BigDecimal monthlyRent;
    private Apartment.ApartmentStatus status;
    private String description;

    // Constructors
    public ApartmentDto() {
    }

    public ApartmentDto(Apartment apartment) {
        this.id = apartment.getId();
        this.unitNumber = apartment.getApartmentNumber();
        this.buildingId = apartment.getBuildingId();
        this.buildingName = apartment.getBuilding() != null ? apartment.getBuilding().getName() : null;
        this.floor = apartment.getFloor();
        this.bedrooms = apartment.getBedrooms();
        this.bathrooms = apartment.getBathrooms();
        this.squareFootage = apartment.getSquareFootage();
        this.monthlyRent = apartment.getMonthlyRent();
        this.status = apartment.getStatus();
        this.description = apartment.getDescription();
    }

    public Apartment toEntity() {
        Apartment apartment = new Apartment();
        apartment.setId(this.id);
        apartment.setApartmentNumber(this.unitNumber);
        apartment.setBuildingId(this.buildingId);
        apartment.setFloor(this.floor);
        apartment.setBedrooms(this.bedrooms);
        apartment.setBathrooms(this.bathrooms);
        apartment.setSquareFootage(this.squareFootage);
        apartment.setMonthlyRent(this.monthlyRent);
        apartment.setStatus(this.status);
        apartment.setDescription(this.description);
        return apartment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
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
