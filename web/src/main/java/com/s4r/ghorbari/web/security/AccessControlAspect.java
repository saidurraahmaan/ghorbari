package com.s4r.ghorbari.core.security;

import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Aspect for enforcing role-based access control on service methods
 * Uses Spring AOP (already included with Spring Framework)
 */
@Aspect
@Component
public class AccessControlAspect {

    private static final Logger logger = LoggerFactory.getLogger(AccessControlAspect.class);

    @Before("@annotation(com.s4r.ghorbari.core.security.RequiresRole)")
    public void checkRequiredRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RequiresRole annotation = method.getAnnotation(RequiresRole.class);
        RoleName[] requiredRoles = annotation.value();
        String errorMessage = annotation.message();

        Set<String> userRoles = getCurrentUserRoles();

        boolean hasAccess = Arrays.stream(requiredRoles)
                .map(RoleName::name)
                .anyMatch(userRoles::contains);

        if (!hasAccess) {
            logger.warn("Access denied for method: {}. Required roles: {}, User roles: {}",
                    method.getName(), Arrays.toString(requiredRoles), userRoles);
            throw new ServiceException(ErrorCode.ACCESS_DENIED, errorMessage);
        }
    }

    private Set<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED, "Authentication required");
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
