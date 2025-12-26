package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tenants")
public class Tenant extends BaseEntity {

    @NotBlank(message = "Tenant key is required")
    @Size(max = 100, message = "Tenant key must be less than 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    private String tenantKey;

    @NotBlank(message = "Tenant name is required")
    @Size(max = 200, message = "Tenant name must be less than 200 characters")
    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    public Tenant() {
    }

    public Tenant(String tenantKey, String name) {
        this.tenantKey = tenantKey;
        this.name = name;
    }

    // Getters and Setters
    public String getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
