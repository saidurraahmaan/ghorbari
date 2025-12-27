package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.service.IUserService;
import com.s4r.ghorbari.web.dto.JwtResponse;
import com.s4r.ghorbari.web.dto.LoginRequest;
import com.s4r.ghorbari.web.dto.RegisterRequest;
import com.s4r.ghorbari.web.dto.UserInfoResponse;
import com.s4r.ghorbari.web.exception.ErrorResponse;
import com.s4r.ghorbari.web.exception.UnauthorizedException;
import com.s4r.ghorbari.web.security.IJwtUtils;
import com.s4r.ghorbari.web.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "Authentication", description = "Authentication and user registration endpoints")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IJwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                         IUserService userService,
                         PasswordEncoder passwordEncoder,
                         IJwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Operation(summary = "User login", description = "Authenticate user and receive JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Tenant context is already set by TenantInterceptor from subdomain
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new UnauthorizedException("Tenant context not found");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(authentication, tenantId);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        JwtResponse response = new JwtResponse(
                jwt,
                userDetails.id(),
                userDetails.getUsername(),
                userDetails.email(),
                tenantId,
                roles
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register new user", description = "Create a new user account for a tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already in use",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Tenant context is already set by TenantInterceptor from subdomain
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new UnauthorizedException("Tenant context not found");
        }

        userService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                tenantId
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get current user", description = "Get currently authenticated user information",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {

            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(new UserInfoResponse(
                    userDetails.id(),
                    userDetails.getUsername(),
                    userDetails.email(),
                    userDetails.tenantId(),
                    roles
            ));
        }
        throw new UnauthorizedException("Not authenticated");
    }
}
