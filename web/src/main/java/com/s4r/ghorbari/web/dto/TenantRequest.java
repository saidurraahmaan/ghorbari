package com.s4r.ghorbari.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TenantRequest {

    @NotBlank(message = "Tenant key is required")
    @Size(max = 100, message = "Tenant key must be less than 100 characters")
    private String tenantKey;

    @NotBlank(message = "Tenant name is required")
    @Size(max = 200, message = "Tenant name must be less than 200 characters")
    private String name;

    private Boolean active = true;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    public TenantRequest() {
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
