package com.s4r.ghorbari.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @NotBlank(message = "Role name is required")
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Size(max = 255)
    @Column(length = 255)
    private String description;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }

    public enum RoleName {
        ROLE_SUPER_ADMIN,   // Platform admin (manages all tenants)
        ROLE_TENANT_ADMIN,  // Tenant admin (manages their apartment complex)
        ROLE_MANAGER,       // Property manager
        ROLE_STAFF,         // Staff member
        ROLE_RESIDENT       // Resident/tenant
    }

    // Getters and Setters
    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
