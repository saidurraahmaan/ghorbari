package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "apartments")
public class Apartment extends TenantAwareEntity {

    @NotBlank(message = "Apartment number is required")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String apartmentNumber;

    @Size(max = 100)
    @Column(length = 100)
    private String building;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer bedrooms;

    @Column(nullable = false)
    private Integer bathrooms;

    @Column(precision = 10, scale = 2)
    private BigDecimal squareFootage;

    @Column(precision = 10, scale = 2)
    private BigDecimal rentAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApartmentStatus status = ApartmentStatus.VACANT;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    public Apartment() {
    }

    public Apartment(String apartmentNumber, Integer floor, Integer bedrooms, Integer bathrooms) {
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
    }

    public enum ApartmentStatus {
        VACANT,
        OCCUPIED,
        MAINTENANCE,
        RESERVED
    }

    // Getters and Setters
    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
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

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
