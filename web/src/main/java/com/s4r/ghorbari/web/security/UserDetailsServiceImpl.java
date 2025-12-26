package com.s4r.ghorbari.web.security;

import com.s4r.ghorbari.core.entity.User;
import com.s4r.ghorbari.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserByEmailAndTenantId(String email, Long tenantId) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndTenantId(email, tenantId)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email: " + email + " and tenantId: " + tenantId));

        return UserDetailsImpl.build(user);
    }
}
