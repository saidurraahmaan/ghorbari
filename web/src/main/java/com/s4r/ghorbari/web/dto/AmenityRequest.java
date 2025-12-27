package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.entity.Amenity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AmenityRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Type is required")
    private Amenity.AmenityType type;

    private String location;

    private Integer maxCapacity;

    private Integer bookingDurationMinutes;

    private Integer advanceBookingDays;

    private Boolean isBookable;

    private Boolean requiresApproval;

    private Amenity.AmenityStatus status;

    private String operatingHours;

    private String rules;

    private String notes;

    // Constructors
    public AmenityRequest() {
    }

    public AmenityRequest(String name, Amenity.AmenityType type) {
        this.name = name;
        this.type = type;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Amenity.AmenityType getType() {
        return type;
    }

    public void setType(Amenity.AmenityType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getBookingDurationMinutes() {
        return bookingDurationMinutes;
    }

    public void setBookingDurationMinutes(Integer bookingDurationMinutes) {
        this.bookingDurationMinutes = bookingDurationMinutes;
    }

    public Integer getAdvanceBookingDays() {
        return advanceBookingDays;
    }

    public void setAdvanceBookingDays(Integer advanceBookingDays) {
        this.advanceBookingDays = advanceBookingDays;
    }

    public Boolean getIsBookable() {
        return isBookable;
    }

    public void setIsBookable(Boolean isBookable) {
        this.isBookable = isBookable;
    }

    public Boolean getRequiresApproval() {
        return requiresApproval;
    }

    public void setRequiresApproval(Boolean requiresApproval) {
        this.requiresApproval = requiresApproval;
    }

    public Amenity.AmenityStatus getStatus() {
        return status;
    }

    public void setStatus(Amenity.AmenityStatus status) {
        this.status = status;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
