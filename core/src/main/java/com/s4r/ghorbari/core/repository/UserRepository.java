package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndTenantId(String email, Long tenantId);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndTenantId(String email, Long tenantId);
}
