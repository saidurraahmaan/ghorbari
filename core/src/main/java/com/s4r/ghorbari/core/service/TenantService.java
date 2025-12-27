package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.TenantDto;
import com.s4r.ghorbari.core.entity.Tenant;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TenantService implements ITenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public TenantDto createTenant(TenantDto tenantDto) {
        Tenant tenant = new Tenant();
        tenant.setTenantKey(tenantDto.getTenantKey());
        tenant.setName(tenantDto.getName());
        tenant.setActive(tenantDto.getActive() != null ? tenantDto.getActive() : true);
        tenant.setDescription(tenantDto.getDescription());

        Tenant savedTenant = tenantRepository.save(tenant);
        return mapToDto(savedTenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantDto> getTenantById(Long id) {
        return tenantRepository.findById(id)
                .map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantDto> getTenantByKey(String tenantKey) {
        return tenantRepository.findByTenantKey(tenantKey)
                .map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantDto> getActiveTenantByKey(String tenantKey) {
        return tenantRepository.findByTenantKeyAndActiveTrue(tenantKey)
                .map(this::mapToDto);
    }

    @Override
    public TenantDto updateTenant(Long id, TenantDto tenantDto) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.TENANT_NOT_FOUND, id));

        tenant.setTenantKey(tenantDto.getTenantKey());
        tenant.setName(tenantDto.getName());
        tenant.setActive(tenantDto.getActive());
        tenant.setDescription(tenantDto.getDescription());

        Tenant updatedTenant = tenantRepository.save(tenant);
        return mapToDto(updatedTenant);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.TENANT_NOT_FOUND, id));
        tenantRepository.delete(tenant);
    }

    @Override
    public void activateTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.TENANT_NOT_FOUND, id));
        tenant.setActive(true);
        tenantRepository.save(tenant);
    }

    @Override
    public void deactivateTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.TENANT_NOT_FOUND, id));
        tenant.setActive(false);
        tenantRepository.save(tenant);
    }

    private TenantDto mapToDto(Tenant tenant) {
        TenantDto dto = new TenantDto();
        dto.setId(tenant.getId());
        dto.setTenantKey(tenant.getTenantKey());
        dto.setName(tenant.getName());
        dto.setActive(tenant.getActive());
        dto.setDescription(tenant.getDescription());
        dto.setCreatedAt(tenant.getCreatedAt());
        dto.setUpdatedAt(tenant.getUpdatedAt());
        return dto;
    }
}
