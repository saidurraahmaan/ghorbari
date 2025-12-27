package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Amenity;

public class AmenityDto {

    private Long id;
    private String name;
    private String description;
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
    public AmenityDto() {
    }

    public AmenityDto(Amenity amenity) {
        this.id = amenity.getId();
        this.name = amenity.getName();
        this.description = amenity.getDescription();
        this.type = amenity.getType();
        this.location = amenity.getLocation();
        this.maxCapacity = amenity.getMaxCapacity();
        this.bookingDurationMinutes = amenity.getBookingDurationMinutes();
        this.advanceBookingDays = amenity.getAdvanceBookingDays();
        this.isBookable = amenity.getIsBookable();
        this.requiresApproval = amenity.getRequiresApproval();
        this.status = amenity.getStatus();
        this.operatingHours = amenity.getOperatingHours();
        this.rules = amenity.getRules();
        this.notes = amenity.getNotes();
    }

    public Amenity toEntity() {
        Amenity amenity = new Amenity();
        amenity.setId(this.id);
        amenity.setName(this.name);
        amenity.setDescription(this.description);
        amenity.setType(this.type);
        amenity.setLocation(this.location);
        amenity.setMaxCapacity(this.maxCapacity);
        amenity.setBookingDurationMinutes(this.bookingDurationMinutes);
        amenity.setAdvanceBookingDays(this.advanceBookingDays);
        amenity.setIsBookable(this.isBookable);
        amenity.setRequiresApproval(this.requiresApproval);
        amenity.setStatus(this.status);
        amenity.setOperatingHours(this.operatingHours);
        amenity.setRules(this.rules);
        amenity.setNotes(this.notes);
        return amenity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
