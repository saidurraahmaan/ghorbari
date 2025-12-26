package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.entity.User;

import java.util.Optional;

public interface IUserService {

    User registerUser(String username, String email, String encodedPassword, Long tenantId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndTenantId(String email, Long tenantId);

    boolean existsByEmailAndTenantId(String email, Long tenantId);
}
