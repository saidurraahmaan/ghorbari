package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.entity.Role;
import com.s4r.ghorbari.core.entity.User;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.RoleRepository;
import com.s4r.ghorbari.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void registerUser(String username, String email, String encodedPassword, String firstName, String lastName, Long tenantId) {
        TenantContext.setCurrentTenantId(tenantId);

        try {
            if (userRepository.existsByEmailAndTenantId(email, tenantId)) {
                throw new ServiceException(ErrorCode.USER_ALREADY_EXISTS, email);
            }

            User user = new User(username, email, encodedPassword, firstName, lastName);
            user.setTenantId(tenantId);

            // Assign default role
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(RoleName.ROLE_RESIDENT)
                    .orElseThrow(() -> new ServiceException(ErrorCode.DEFAULT_ROLE_NOT_FOUND, "ROLE_RESIDENT"));
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);
        } finally {
            TenantContext.clear();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByEmailAndTenantId(String email, Long tenantId) {
        return userRepository.findByEmailAndTenantId(email, tenantId);
    }

    @Override
    public List<User> findAllByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    @Override
    public boolean existsByEmailAndTenantId(String email, Long tenantId) {
        return userRepository.existsByEmailAndTenantId(email, tenantId);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUserProfile(Long userId, String firstName, String lastName, String phoneNumber,
                                  LocalDate dateOfBirth, String nationalId, String passportNumber,
                                  LocalDate passportExpiryDate, String nationality, String emergencyContactName,
                                  String emergencyContactPhone, String emergencyContactRelationship) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        User user = userRepository.findByIdAndTenantId(userId, tenantId)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "User not found"));

        // Update personal fields (only if provided - null means no change)
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        if (dateOfBirth != null) {
            user.setDateOfBirth(dateOfBirth);
        }
        if (nationalId != null) {
            user.setNationalId(nationalId);
        }
        if (passportNumber != null) {
            user.setPassportNumber(passportNumber);
        }
        if (passportExpiryDate != null) {
            user.setPassportExpiryDate(passportExpiryDate);
        }
        if (nationality != null) {
            user.setNationality(nationality);
        }
        if (emergencyContactName != null) {
            user.setEmergencyContactName(emergencyContactName);
        }
        if (emergencyContactPhone != null) {
            user.setEmergencyContactPhone(emergencyContactPhone);
        }
        if (emergencyContactRelationship != null) {
            user.setEmergencyContactRelationship(emergencyContactRelationship);
        }

        userRepository.save(user);
    }
}
