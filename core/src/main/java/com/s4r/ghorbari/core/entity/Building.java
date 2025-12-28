package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buildings")
public class Building extends TenantAwareEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String address;

    @Column(name = "total_floors")
    private Integer totalFloors;

    @Column(name = "total_units")
    private Integer totalUnits;

    @Column(name = "year_built")
    private Integer yearBuilt;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuildingStatus status = BuildingStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "building_managers",
        joinColumns = @JoinColumn(name = "building_id"),
        inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private Set<User> managers = new HashSet<>();

    public enum BuildingStatus {
        ACTIVE,
        UNDER_CONSTRUCTION,
        MAINTENANCE,
        INACTIVE
    }

    // Constructors
    public Building() {
    }

    public Building(String name, String address) {
        this.name = name;
        this.address = address;
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

    public BuildingStatus getStatus() {
        return status;
    }

    public void setStatus(BuildingStatus status) {
        this.status = status;
    }

    public Set<User> getManagers() {
        return managers;
    }

    public void setManagers(Set<User> managers) {
        this.managers = managers;
    }

    public void addManager(User manager) {
        this.managers.add(manager);
    }

    public void removeManager(User manager) {
        this.managers.remove(manager);
    }
}
