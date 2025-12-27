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
            tenantId = resolveTenantId(request);

            if (tenantId != null) {
                TenantContext.setCurrentTenantId(tenantId);
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

    /**
     * Resolve tenant ID from request
     * Strategy: Frontend sends X-Tenant-Key header -> lookup tenant in DB -> return tenant ID
     */
    private Long resolveTenantId(HttpServletRequest request) {
        // Strategy 1: Extract from JWT token (for authenticated requests)
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            Long tenantId = jwtUtils.getTenantIdFromJwtToken(jwt);
            if (tenantId != null) {
                return tenantId;
            }
        }

        // Strategy 2: Extract from X-Tenant-Key header (frontend sends tenant key)
        String tenantKey = request.getHeader("X-Tenant-Key");
        if (tenantKey != null && !tenantKey.isEmpty()) {
            Optional<Tenant> tenant = tenantRepository.findByTenantKeyAndActiveTrue(tenantKey);
            if (tenant.isPresent()) {
                return tenant.get().getId();
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

}
