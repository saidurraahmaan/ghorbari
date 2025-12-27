package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.ResidentDto;
import com.s4r.ghorbari.core.entity.Resident;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.ResidentRepository;
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
    public void createResident(ResidentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
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
    public Optional<ResidentDto> getResidentByEmail(String email) {
        return residentRepository.findByEmail(email)
                .map(ResidentDto::new);
    }

    @Override
    public List<ResidentDto> getResidentsByStatus(Resident.ResidentStatus status) {
        return residentRepository.findByStatus(status).stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResidentDto> getResidentsByApartmentId(Long apartmentId) {
        return residentRepository.findByApartmentId(apartmentId).stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateResident(Long id, ResidentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        residentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Resident not found"));

        Resident resident = dto.toEntity();
        resident.setId(id);
        resident.setTenantId(tenantId);
        residentRepository.save(resident);
    }

    @Override
    public void deleteResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Resident not found"));

        residentRepository.delete(resident);
    }
}
