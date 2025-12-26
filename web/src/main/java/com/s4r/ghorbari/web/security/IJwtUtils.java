package com.s4r.ghorbari.web.security;

import org.springframework.security.core.Authentication;

public interface IJwtUtils {

    String generateJwtToken(Authentication authentication, Long tenantId);

    String generateTokenFromUserId(Long userId, String username, String email, Long tenantId);

    String getUsernameFromJwtToken(String token);

    Long getTenantIdFromJwtToken(String token);

    Long getUserIdFromJwtToken(String token);

    boolean validateJwtToken(String authToken);
}
