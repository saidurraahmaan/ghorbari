package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findByTenantId(Long tenantId);

    Optional<Building> findByIdAndTenantId(Long id, Long tenantId);

    List<Building> findByTenantIdAndStatus(Long tenantId, Building.BuildingStatus status);

    // Manager-related queries
    List<Building> findByManagersId(Long managerId);
}
