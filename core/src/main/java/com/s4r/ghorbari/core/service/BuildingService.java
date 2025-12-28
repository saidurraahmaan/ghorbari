package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.BuildingDto;
import com.s4r.ghorbari.core.entity.Building;
import com.s4r.ghorbari.core.entity.User;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.BuildingRepository;
import com.s4r.ghorbari.core.repository.UserRepository;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuildingService implements IBuildingService {

    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;

    public BuildingService(BuildingRepository buildingRepository, UserRepository userRepository) {
        this.buildingRepository = buildingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN})
    public void createBuilding(BuildingDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        Building building = dto.toEntity();
        building.setTenantId(tenantId);
        building.setStatus(Building.BuildingStatus.ACTIVE);
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
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
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
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN})
    public void deleteBuilding(Long id) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Building building = buildingRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Building not found"));

        buildingRepository.delete(building);
    }

    @Override
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN})
    public void assignManagerToBuilding(Long buildingId, Long managerId) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Building building = buildingRepository.findByIdAndTenantId(buildingId, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Building not found"));

        User manager = userRepository.findByIdAndTenantId(managerId, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Manager user not found"));

        // Verify user has MANAGER role
        boolean isManager = manager.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleName.ROLE_MANAGER);

        if (!isManager) {
            throw new ServiceException(ErrorCode.INVALID_OPERATION, "User must have ROLE_MANAGER to be assigned to buildings");
        }

        building.addManager(manager);
        buildingRepository.save(building);
    }

    @Override
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN})
    public void removeManagerFromBuilding(Long buildingId, Long managerId) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Building building = buildingRepository.findByIdAndTenantId(buildingId, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Building not found"));

        User manager = userRepository.findByIdAndTenantId(managerId, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Manager user not found"));

        building.removeManager(manager);
        buildingRepository.save(building);
    }

    @Override
    public List<BuildingDto> getBuildingsByManager(Long managerId) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        return buildingRepository.findByManagersId(managerId).stream()
                .filter(building -> building.getTenantId().equals(tenantId))
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }
}
