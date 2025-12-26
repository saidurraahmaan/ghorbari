package com.s4r.ghorbari.core.exception;

public enum ErrorCode {

    // User related errors (1000-1999)
    USER_ALREADY_EXISTS(1001, "User already exists with email: %s"),
    USER_NOT_FOUND(1002, "User not found with id: %s"),
    INVALID_USER_DATA(1003, "Invalid user data: %s"),
    USER_EMAIL_REQUIRED(1004, "User email is required"),
    USER_PASSWORD_REQUIRED(1005, "User password is required"),

    // Role related errors (2000-2999)
    ROLE_NOT_FOUND(2001, "Role not found: %s"),
    ROLE_ALREADY_EXISTS(2002, "Role already exists: %s"),
    DEFAULT_ROLE_NOT_FOUND(2003, "Default role %s not found. Please seed roles first"),

    // Tenant related errors (3000-3999)
    TENANT_NOT_FOUND(3001, "Tenant not found with id: %s"),
    TENANT_ALREADY_EXISTS(3002, "Tenant already exists: %s"),
    TENANT_ID_REQUIRED(3003, "Tenant ID is required"),

    // Resource related errors (4000-4999)
    RESOURCE_NOT_FOUND(4001, "Resource not found: %s"),
    RESOURCE_ALREADY_EXISTS(4002, "Resource already exists: %s"),

    // Business logic errors (5000-5999)
    INVALID_OPERATION(5001, "Invalid operation: %s"),
    OPERATION_NOT_ALLOWED(5002, "Operation not allowed: %s"),

    // Data integrity errors (6000-6999)
    DATA_INTEGRITY_VIOLATION(6001, "Data integrity violation: %s"),
    CONSTRAINT_VIOLATION(6002, "Constraint violation: %s");

    private final int code;
    private final String messageTemplate;

    ErrorCode(int code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }

    public int getCode() {
        return code;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String formatMessage(Object... args) {
        if (args == null || args.length == 0) {
            return messageTemplate;
        }
        return String.format(messageTemplate, args);
    }
}
