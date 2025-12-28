package com.s4r.ghorbari.web.dto;

import com.s4r.ghorbari.core.entity.User;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfoResponse {

    private Long userId;
    private String username;
    private String email;
    private Long tenantId;
    private Set<String> roles;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String nationalId;
    private String passportNumber;
    private LocalDate passportExpiryDate;
    private String nationality;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;

    public UserInfoResponse() {
    }

    public UserInfoResponse(Long userId, String username, String email, Long tenantId, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.tenantId = tenantId;
        this.roles = roles;
    }

    public UserInfoResponse(Long userId, String username, String email, Long tenantId, Set<String> roles,
                           String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth,
                           String nationalId, String passportNumber, LocalDate passportExpiryDate,
                           String nationality, String emergencyContactName, String emergencyContactPhone,
                           String emergencyContactRelationship) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.tenantId = tenantId;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.passportNumber = passportNumber;
        this.passportExpiryDate = passportExpiryDate;
        this.nationality = nationality;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public UserInfoResponse(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.tenantId = user.getTenantId();
        this.roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.dateOfBirth = user.getDateOfBirth();
        this.nationalId = user.getNationalId();
        this.passportNumber = user.getPassportNumber();
        this.passportExpiryDate = user.getPassportExpiryDate();
        this.nationality = user.getNationality();
        this.emergencyContactName = user.getEmergencyContactName();
        this.emergencyContactPhone = user.getEmergencyContactPhone();
        this.emergencyContactRelationship = user.getEmergencyContactRelationship();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(LocalDate passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }
}
