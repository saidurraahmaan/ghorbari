package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.BuildingDto;
import com.s4r.ghorbari.core.entity.Building;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.BuildingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuildingService implements IBuildingService {

    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public void createBuilding(BuildingDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        Building building = dto.toEntity();
        building.setTenantId(tenantId);
        buildingRepository.save(building);
    }

    @Override
    public List<BuildingDto> getAllBuildings() {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        return buildingRepository.findByTenantId(tenantId).stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BuildingDto> getBuildingById(Long id) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        return buildingRepository.findByIdAndTenantId(id, tenantId)
                .map(BuildingDto::new);
    }

    @Override
    public List<BuildingDto> getBuildingsByStatus(Building.BuildingStatus status) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        return buildingRepository.findByTenantIdAndStatus(tenantId, status).stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBuilding(Long id, BuildingDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        buildingRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Building not found"));

        Building building = dto.toEntity();
        building.setId(id);
        building.setTenantId(tenantId);
        buildingRepository.save(building);
    }

    @Override
    public void deleteBuilding(Long id) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Building building = buildingRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Building not found"));

        buildingRepository.delete(building);
    }
}
