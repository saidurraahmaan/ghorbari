package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.ApartmentDto;
import com.s4r.ghorbari.core.entity.Apartment;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.ApartmentRepository;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApartmentService implements IApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void createApartment(ApartmentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        Apartment apartment = dto.toEntity();
        apartment.setTenantId(tenantId);
        apartmentRepository.save(apartment);
    }

    @Override
    public Optional<ApartmentDto> getApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .map(ApartmentDto::new);
    }

    @Override
    public List<ApartmentDto> getAllApartments() {
        return apartmentRepository.findAll().stream()
                .map(ApartmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentDto> getApartmentsByStatus(Apartment.ApartmentStatus status) {
        return apartmentRepository.findByStatus(status).stream()
                .map(ApartmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentDto> getApartmentsByBuildingId(Long buildingId) {
        return apartmentRepository.findByBuildingId(buildingId).stream()
                .map(ApartmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateApartment(Long id, ApartmentDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        apartmentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Apartment not found"));

        Apartment apartment = dto.toEntity();
        apartment.setId(id);
        apartment.setTenantId(tenantId);
        apartmentRepository.save(apartment);
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void deleteApartment(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Apartment not found"));

        apartmentRepository.delete(apartment);
    }
}
