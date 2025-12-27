package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.TenantDto;

import java.util.List;
import java.util.Optional;

public interface ITenantService {

    /**
     * Create a new tenant
     */
    TenantDto createTenant(TenantDto tenantDto);

    /**
     * Get all tenants
     */
    List<TenantDto> getAllTenants();

    /**
     * Get tenant by ID
     */
    Optional<TenantDto> getTenantById(Long id);

    /**
     * Get tenant by key (for public access)
     */
    Optional<TenantDto> getTenantByKey(String tenantKey);

    /**
     * Get active tenant by key
     */
    Optional<TenantDto> getActiveTenantByKey(String tenantKey);

    /**
     * Update tenant
     */
    TenantDto updateTenant(Long id, TenantDto tenantDto);

    /**
     * Delete tenant
     */
    void deleteTenant(Long id);

    /**
     * Activate tenant
     */
    void activateTenant(Long id);

    /**
     * Deactivate tenant
     */
    void deactivateTenant(Long id);
}
