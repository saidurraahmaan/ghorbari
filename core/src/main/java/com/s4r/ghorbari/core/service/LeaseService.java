package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.LeaseDto;
import com.s4r.ghorbari.core.entity.Lease;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.LeaseRepository;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaseService implements ILeaseService {

    private final LeaseRepository leaseRepository;

    public LeaseService(LeaseRepository leaseRepository) {
        this.leaseRepository = leaseRepository;
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void createLease(LeaseDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        Lease lease = dto.toEntity();
        lease.setTenantId(tenantId);
        leaseRepository.save(lease);
    }

    @Override
    public List<LeaseDto> getAllLeases() {
        return leaseRepository.findAll().stream()
                .map(LeaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LeaseDto> getLeaseById(Long id) {
        return leaseRepository.findById(id)
                .map(LeaseDto::new);
    }

    @Override
    public List<LeaseDto> getLeasesByStatus(Lease.LeaseStatus status) {
        return leaseRepository.findByStatus(status).stream()
                .map(LeaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaseDto> getLeasesByApartmentId(Long apartmentId) {
        return leaseRepository.findByApartmentId(apartmentId).stream()
                .map(LeaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaseDto> getLeasesByResidentId(Long residentId) {
        return leaseRepository.findByPrimaryResidentId(residentId).stream()
                .map(LeaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaseDto> getExpiringLeases(LocalDate beforeDate) {
        LocalDate today = LocalDate.now();
        return leaseRepository.findExpiringLeases(today, beforeDate).stream()
                .map(LeaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LeaseDto> getActiveLeaseByApartment(Long apartmentId) {
        return leaseRepository.findActiveLeaseByApartment(apartmentId)
                .map(LeaseDto::new);
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void updateLease(Long id, LeaseDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        leaseRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Lease not found"));

        Lease lease = dto.toEntity();
        lease.setId(id);
        lease.setTenantId(tenantId);
        leaseRepository.save(lease);
    }

    @Override
    public void terminateLease(Long id, LocalDate terminationDate, String reason) {
        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Lease not found"));

        lease.setStatus(Lease.LeaseStatus.TERMINATED);
        lease.setTerminationDate(terminationDate);
        lease.setTerminationReason(reason);
        leaseRepository.save(lease);
    }

    @Override
    public void renewLease(Long id, LocalDate newEndDate) {
        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Lease not found"));

        lease.setEndDate(newEndDate);
        lease.setStatus(Lease.LeaseStatus.RENEWED);
        leaseRepository.save(lease);
    }

    @Override
    @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
    public void deleteLease(Long id) {
        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Lease not found"));

        leaseRepository.delete(lease);
    }
}
