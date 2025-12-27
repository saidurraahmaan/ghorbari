package com.s4r.ghorbari.core.security;

import com.s4r.ghorbari.core.entity.Role.RoleName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify required roles for service methods
 * User must have at least one of the specified roles
 *
 * Usage:
 * @RequiresRole(RoleName.ROLE_TENANT_ADMIN)
 * @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    RoleName[] value();
    String message() default "Access denied: Insufficient permissions";
}
