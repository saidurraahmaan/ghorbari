package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.AmenityDto;
import com.s4r.ghorbari.core.entity.Amenity;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.AmenityRepository;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmenityService implements IAmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void createAmenity(AmenityDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        Amenity amenity = dto.toEntity();
        amenity.setTenantId(tenantId);
        amenityRepository.save(amenity);
    }

    @Override
    public List<AmenityDto> getAllAmenities() {
        return amenityRepository.findAll().stream()
                .map(AmenityDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AmenityDto> getAmenityById(Long id) {
        return amenityRepository.findById(id)
                .map(AmenityDto::new);
    }

    @Override
    public List<AmenityDto> getAmenitiesByType(Amenity.AmenityType type) {
        return amenityRepository.findByType(type).stream()
                .map(AmenityDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityDto> getAmenitiesByStatus(Amenity.AmenityStatus status) {
        return amenityRepository.findByStatus(status).stream()
                .map(AmenityDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityDto> getBookableAmenities() {
        return amenityRepository.findByIsBookable(true).stream()
                .map(AmenityDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityDto> getAvailableBookableAmenities() {
        return amenityRepository.findByStatusAndIsBookable(Amenity.AmenityStatus.AVAILABLE, true).stream()
                .map(AmenityDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateAmenity(Long id, AmenityDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        amenityRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Amenity not found"));

        Amenity amenity = dto.toEntity();
        amenity.setId(id);
        amenity.setTenantId(tenantId);
        amenityRepository.save(amenity);
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void deleteAmenity(Long id) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Amenity not found"));

        amenityRepository.delete(amenity);
    }
}

