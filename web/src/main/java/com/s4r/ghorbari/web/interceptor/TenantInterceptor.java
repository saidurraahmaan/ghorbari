package com.s4r.ghorbari.web.interceptor;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.entity.Tenant;
import com.s4r.ghorbari.core.repository.TenantRepository;
import com.s4r.ghorbari.web.security.IJwtUtils;
import com.s4r.ghorbari.web.security.JwtUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    private final EntityManager entityManager;
    private final IJwtUtils jwtUtils;
    private final TenantRepository tenantRepository;

    public TenantInterceptor(EntityManager entityManager, IJwtUtils jwtUtils, TenantRepository tenantRepository) {
        this.entityManager = entityManager;
        this.jwtUtils = jwtUtils;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Check if tenant context is already set by JWT filter
        Long tenantId = TenantContext.getCurrentTenantId();

        // If not set by JWT, try other strategies
        if (tenantId == null) {
            String tenantIdStr = extractTenantId(request);

            if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
                try {
                    tenantId = Long.parseLong(tenantIdStr);
                    TenantContext.setCurrentTenantId(tenantId);
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return false;
                }
            }
        }

        // Enable Hibernate filter if tenant is set
        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("tenantFilter")
                   .setParameter("tenantId", tenantId);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // Don't clear if it was set by JWT filter - let the security context handle it
        // TenantContext.clear();
    }

    private String extractTenantId(HttpServletRequest request) {
        // Strategy 1: Extract from JWT token
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            Long tenantId = jwtUtils.getTenantIdFromJwtToken(jwt);
            if (tenantId != null) {
                return tenantId.toString();
            }
        }

        // Strategy 2: Extract from subdomain
        String tenantId = extractFromSubdomain(request);
        if (tenantId != null && !tenantId.isEmpty()) {
            return tenantId;
        }

        // Strategy 3: Fallback to header (for development/testing)
        tenantId = request.getHeader(TENANT_HEADER);
        if (tenantId != null && !tenantId.isEmpty()) {
            return tenantId;
        }

        // Strategy 4: Fallback to X-Tenant-Key header (tenant key instead of ID)
        String tenantKey = request.getHeader("X-Tenant-Key");
        if (tenantKey != null && !tenantKey.isEmpty()) {
            Optional<Tenant> tenant = tenantRepository.findByTenantKeyAndActiveTrue(tenantKey);
            if (tenant.isPresent()) {
                return tenant.get().getId().toString();
            }
        }

        return null;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    private String extractFromSubdomain(HttpServletRequest request) {
        String serverName = request.getServerName();

        // Skip if serverName is null or IP address
        if (serverName == null || serverName.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            return null;
        }

        // Split by dot
        String[] parts = serverName.split("\\.");

        String tenantKey = null;

        // Handle localhost subdomains: tenant1.localhost
        if (parts.length == 2 && parts[1].equals("localhost")) {
            tenantKey = parts[0];
        }
        // Handle production subdomains: tenant1.example.com or tenant1.example.co.uk
        else if (parts.length >= 3) {
            tenantKey = parts[0]; // First part is the subdomain (tenant key)
        }

        // Look up tenant by tenantKey in database
        if (tenantKey != null && !tenantKey.isEmpty()) {
            Optional<Tenant> tenant = tenantRepository.findByTenantKeyAndActiveTrue(tenantKey);

            if (tenant.isPresent()) {
                return tenant.get().getId().toString();
            }
        }

        return null;
    }
}
