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
    public void registerUser(String username, String email, String encodedPassword, Long tenantId) {
        TenantContext.setCurrentTenantId(tenantId);

        try {
            if (userRepository.existsByEmailAndTenantId(email, tenantId)) {
                throw new ServiceException(ErrorCode.USER_ALREADY_EXISTS, email);
            }

            User user = new User(username, email, encodedPassword);
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
}
