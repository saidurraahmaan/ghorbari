package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.ResidentDto;
import com.s4r.ghorbari.core.entity.Resident;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.ResidentRepository;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResidentService implements IResidentService {

    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void createResident(ResidentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        // Check if user is already a resident of this apartment
        Optional<Resident> existing = residentRepository.findByUserIdAndApartmentId(
                dto.getUserId(), dto.getApartmentId());
        if (existing.isPresent()) {
            throw new ServiceException(ErrorCode.INVALID_OPERATION,
                    "User is already a resident of this apartment");
        }

        // If trying to set as primary resident, check if apartment already has a primary resident
        if (Boolean.TRUE.equals(dto.getIsPrimaryResident())) {
            Optional<Resident> primaryResident = residentRepository.findByApartmentIdAndIsPrimaryResident(
                    dto.getApartmentId(), true);
            if (primaryResident.isPresent()) {
                throw new ServiceException(ErrorCode.INVALID_OPERATION,
                        "Apartment already has a primary resident");
            }
        }

        Resident resident = dto.toEntity();
        resident.setTenantId(tenantId);
        residentRepository.save(resident);
    }

    @Override
    public List<ResidentDto> getAllResidents() {
        return residentRepository.findAll().stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResidentDto> getResidentById(Long id) {
        return residentRepository.findById(id)
                .map(ResidentDto::new);
    }

    @Override
    public List<ResidentDto> getResidentsByApartmentId(Long apartmentId) {
        return residentRepository.findByApartmentId(apartmentId).stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResidentDto> getResidentsByUserId(Long userId) {
        return residentRepository.findByUserId(userId).stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResidentDto> getPrimaryResidentByApartmentId(Long apartmentId) {
        return residentRepository.findByApartmentIdAndIsPrimaryResident(apartmentId, true)
                .map(ResidentDto::new);
    }

    @Override
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateResident(Long id, ResidentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        Resident existingResident = residentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Resident not found"));

        // If trying to set as primary resident, check if apartment already has a different primary resident
        if (Boolean.TRUE.equals(dto.getIsPrimaryResident()) && !existingResident.getIsPrimaryResident()) {
            Optional<Resident> primaryResident = residentRepository.findByApartmentIdAndIsPrimaryResident(
                    dto.getApartmentId(), true);
            if (primaryResident.isPresent() && !primaryResident.get().getId().equals(id)) {
                throw new ServiceException(ErrorCode.INVALID_OPERATION,
                        "Apartment already has a primary resident");
            }
        }

        Resident resident = dto.toEntity();
        resident.setId(id);
        resident.setTenantId(tenantId);
        residentRepository.save(resident);
    }

    @Override
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void deleteResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Resident not found"));

        residentRepository.delete(resident);
    }
}
