package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "amenities")
public class Amenity extends TenantAwareEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmenityType type;

    @Column(length = 200)
    private String location;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "booking_duration_minutes")
    private Integer bookingDurationMinutes;

    @Column(name = "advance_booking_days")
    private Integer advanceBookingDays;

    @Column(name = "is_bookable")
    private Boolean isBookable = true;

    @Column(name = "requires_approval")
    private Boolean requiresApproval = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmenityStatus status = AmenityStatus.AVAILABLE;

    @Column(name = "operating_hours", length = 500)
    private String operatingHours;

    @Column(length = 1000)
    private String rules;

    @Column(length = 1000)
    private String notes;

    public enum AmenityType {
        GYM,
        SWIMMING_POOL,
        PARKING,
        PLAYGROUND,
        CLUBHOUSE,
        TENNIS_COURT,
        BASKETBALL_COURT,
        MEETING_ROOM,
        GAME_ROOM,
        ROOFTOP_TERRACE,
        BBQ_AREA,
        LIBRARY,
        THEATER,
        OTHER
    }

    public enum AmenityStatus {
        AVAILABLE,
        UNDER_MAINTENANCE,
        TEMPORARILY_CLOSED,
        PERMANENTLY_CLOSED
    }

    // Constructors
    public Amenity() {
    }

    public Amenity(String name, AmenityType type) {
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

    public AmenityType getType() {
        return type;
    }

    public void setType(AmenityType type) {
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

    public AmenityStatus getStatus() {
        return status;
    }

    public void setStatus(AmenityStatus status) {
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
