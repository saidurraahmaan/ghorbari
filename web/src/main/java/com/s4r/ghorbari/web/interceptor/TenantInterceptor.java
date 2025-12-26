package com.s4r.ghorbari.web.interceptor;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.web.security.JwtUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    private final EntityManager entityManager;
    private final JwtUtils jwtUtils;

    public TenantInterceptor(EntityManager entityManager, JwtUtils jwtUtils) {
        this.entityManager = entityManager;
        this.jwtUtils = jwtUtils;
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

        // Strategy 2: Extract from header
        String tenantId = request.getHeader(TENANT_HEADER);
        if (tenantId != null && !tenantId.isEmpty()) {
            return tenantId;
        }

        // Strategy 3: Extract from subdomain
        return extractFromSubdomain(request);
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

        // Example: tenant1.yourdomain.com -> tenant1
        // This is a simple implementation, adjust based on your domain structure
        if (serverName != null && serverName.contains(".")) {
            String[] parts = serverName.split("\\.");
            if (parts.length > 2) {
                // Return the subdomain part
                // You would need to map subdomain to tenant ID in your database
                return null; // Implement mapping logic here
            }
        }

        return null;
    }
}
