package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.enums.RoleName;

import java.util.Set;

public record LoginResponse(
        String token,
        UserInfo user
) {
    public record UserInfo(
            Long id,
            String username,
            String email,
            Set<RoleName> roles
    ) {}
}
