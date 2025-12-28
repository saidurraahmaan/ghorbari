package com.s4r.ghorbari.web.controller;

import com.s4r.ghorbari.core.entity.User;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import com.s4r.ghorbari.core.service.IUserService;
import com.s4r.ghorbari.web.dto.UserInfoResponse;
import com.s4r.ghorbari.web.dto.UserProfileUpdateRequest;
import com.s4r.ghorbari.web.exception.ErrorResponse;
import com.s4r.ghorbari.web.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "User profile management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user profile", description = "Retrieve the profile of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/profile")
    public ResponseEntity<UserInfoResponse> getCurrentUserProfile() {
        Long currentUserId = getCurrentUserId();
        return userService.findById(currentUserId)
                .map(user -> ResponseEntity.ok(new UserInfoResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update current user profile", description = "Update the profile of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/profile")
    public ResponseEntity<?> updateCurrentUserProfile(@Valid @RequestBody UserProfileUpdateRequest request) {
        Long currentUserId = getCurrentUserId();
        userService.updateUserProfile(
                currentUserId,
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getDateOfBirth(),
                request.getNationalId(),
                request.getPassportNumber(),
                request.getPassportExpiryDate(),
                request.getNationality(),
                request.getEmergencyContactName(),
                request.getEmergencyContactPhone(),
                request.getEmergencyContactRelationship()
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a specific user's profile by their ID (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUserById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(new UserInfoResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update user profile by ID", description = "Update a specific user's profile (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequiresRole({RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateUserProfileById(
            @Parameter(description = "User ID") @PathVariable Long id,
            @Valid @RequestBody UserProfileUpdateRequest request) {
        userService.updateUserProfile(
                id,
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getDateOfBirth(),
                request.getNationalId(),
                request.getPassportNumber(),
                request.getPassportExpiryDate(),
                request.getNationality(),
                request.getEmergencyContactName(),
                request.getEmergencyContactPhone(),
                request.getEmergencyContactRelationship()
        );
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.id();
    }
}
