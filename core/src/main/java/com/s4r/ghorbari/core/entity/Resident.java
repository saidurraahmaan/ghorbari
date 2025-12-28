package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "residents")
public class Resident extends TenantAwareEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "apartment_id", nullable = false)
    private Long apartmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", insertable = false, updatable = false)
    private Apartment apartment;

    @Column(name = "is_primary_resident", nullable = false)
    private Boolean isPrimaryResident = false;

    public Resident() {
    }

    public Resident(Long userId, Long apartmentId, Boolean isPrimaryResident) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Boolean getIsPrimaryResident() {
        return isPrimaryResident;
    }

    public void setIsPrimaryResident(Boolean isPrimaryResident) {
        this.isPrimaryResident = isPrimaryResident;
    }
}
