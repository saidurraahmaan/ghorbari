package com.s4r.ghorbari.web.dto;

import java.util.Set;

public class UserInfoResponse {

    private Long userId;
    private String username;
    private String email;
    private Long tenantId;
    private Set<String> roles;

    public UserInfoResponse() {
    }

    public UserInfoResponse(Long userId, String username, String email, Long tenantId, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.tenantId = tenantId;
        this.roles = roles;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
