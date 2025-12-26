package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByTenantKey(String tenantKey);

    Optional<Tenant> findByTenantKeyAndActiveTrue(String tenantKey);
}
