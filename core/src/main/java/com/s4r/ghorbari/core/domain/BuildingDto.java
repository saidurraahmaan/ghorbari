package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Building;

public class BuildingDto {

    private Long id;
    private String name;
    private String address;
    private Integer totalFloors;
    private Integer totalUnits;
    private Integer yearBuilt;
    private String description;
    private Building.BuildingStatus status;

    // Constructors
    public BuildingDto() {
    }

    public BuildingDto(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.address = building.getAddress();
        this.totalFloors = building.getTotalFloors();
        this.totalUnits = building.getTotalUnits();
        this.yearBuilt = building.getYearBuilt();
        this.description = building.getDescription();
        this.status = building.getStatus();
    }

    public Building toEntity() {
        Building building = new Building();
        building.setId(this.id);
        building.setName(this.name);
        building.setAddress(this.address);
        building.setTotalFloors(this.totalFloors);
        building.setTotalUnits(this.totalUnits);
        building.setYearBuilt(this.yearBuilt);
        building.setDescription(this.description);
        building.setStatus(this.status);
        return building;
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
