package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.entity.Role;
import com.s4r.ghorbari.core.entity.User;
import com.s4r.ghorbari.core.repository.RoleRepository;
import com.s4r.ghorbari.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    public User registerUser(String username, String email, String encodedPassword, Long tenantId) {
        TenantContext.setCurrentTenantId(tenantId);

        try {
            if (userRepository.existsByEmailAndTenantId(email, tenantId)) {
                throw new IllegalArgumentException("Email is already in use for this tenant");
            }

            User user = new User(username, email, encodedPassword);
            user.setTenantId(tenantId);

            // Assign default role
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(Role.RoleName.ROLE_RESIDENT)
                    .orElseThrow(() -> new RuntimeException("Default role ROLE_RESIDENT not found. Please seed roles first."));
            roles.add(userRole);
            user.setRoles(roles);

            return userRepository.save(user);
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
    public boolean existsByEmailAndTenantId(String email, Long tenantId) {
        return userRepository.existsByEmailAndTenantId(email, tenantId);
    }
}
