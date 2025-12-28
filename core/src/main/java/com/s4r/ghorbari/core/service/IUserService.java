package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    void registerUser(String username, String email, String encodedPassword, String firstName, String lastName, Long tenantId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndTenantId(String email, Long tenantId);

    Optional<User> findById(Long id);

    List<User> findAllByEmail(String email);

    boolean existsByEmailAndTenantId(String email, Long tenantId);

    void updateUserProfile(Long userId, String firstName, String lastName, String phoneNumber,
                          LocalDate dateOfBirth, String nationalId, String passportNumber,
                          LocalDate passportExpiryDate, String nationality, String emergencyContactName,
                          String emergencyContactPhone, String emergencyContactRelationship);
}
