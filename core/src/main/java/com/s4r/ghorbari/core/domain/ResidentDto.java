package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Resident;

import java.time.LocalDate;

public class ResidentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Long userId;
    private String nationalId;
    private String passportNumber;
    private LocalDate passportExpiryDate;
    private String nationality;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private Boolean isPrimaryResident;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private Resident.ResidentStatus status;
    private String notes;

    // Constructors
    public ResidentDto() {
    }

    public ResidentDto(Resident resident) {
        this.id = resident.getId();
        this.firstName = resident.getFirstName();
        this.lastName = resident.getLastName();
        this.email = resident.getEmail();
        this.phoneNumber = resident.getPhoneNumber();
        this.dateOfBirth = resident.getDateOfBirth();
        this.userId = resident.getUserId();
        this.nationalId = resident.getNationalId();
        this.passportNumber = resident.getPassportNumber();
        this.passportExpiryDate = resident.getPassportExpiryDate();
        this.nationality = resident.getNationality();
        this.emergencyContactName = resident.getEmergencyContactName();
        this.emergencyContactPhone = resident.getEmergencyContactPhone();
        this.emergencyContactRelationship = resident.getEmergencyContactRelationship();
        this.isPrimaryResident = resident.getIsPrimaryResident();
        this.moveInDate = resident.getMoveInDate();
        this.moveOutDate = resident.getMoveOutDate();
        this.status = resident.getStatus();
        this.notes = resident.getNotes();
    }

    public Resident toEntity() {
        Resident resident = new Resident();
        resident.setId(this.id);
        resident.setFirstName(this.firstName);
        resident.setLastName(this.lastName);
        resident.setEmail(this.email);
        resident.setPhoneNumber(this.phoneNumber);
        resident.setDateOfBirth(this.dateOfBirth);
        resident.setUserId(this.userId);
        resident.setNationalId(this.nationalId);
        resident.setPassportNumber(this.passportNumber);
        resident.setPassportExpiryDate(this.passportExpiryDate);
        resident.setNationality(this.nationality);
        resident.setEmergencyContactName(this.emergencyContactName);
        resident.setEmergencyContactPhone(this.emergencyContactPhone);
        resident.setEmergencyContactRelationship(this.emergencyContactRelationship);
        resident.setIsPrimaryResident(this.isPrimaryResident);
        resident.setMoveInDate(this.moveInDate);
        resident.setMoveOutDate(this.moveOutDate);
        resident.setStatus(this.status);
        resident.setNotes(this.notes);
        return resident;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getIsPrimaryResident() {
        return isPrimaryResident;
    }

    public void setIsPrimaryResident(Boolean isPrimaryResident) {
        this.isPrimaryResident = isPrimaryResident;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }

    public LocalDate getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(LocalDate moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public Resident.ResidentStatus getStatus() {
        return status;
    }

    public void setStatus(Resident.ResidentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
