package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndTenantId(String username, Long tenantId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndTenantId(String email, Long tenantId);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    List<User> findAllByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndTenantId(String email, Long tenantId);

    Boolean existsByUsername(String username);

    Boolean existsByUsernameAndTenantId(String username, Long tenantId);
}
