package com.s4r.ghorbari.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaseRenewalRequest {

    @NotNull(message = "New end date is required")
    private LocalDate newEndDate;

    // Constructors
    public LeaseRenewalRequest() {
    }

    // Getters and Setters
    public LocalDate getNewEndDate() {
        return newEndDate;
    }

    public void setNewEndDate(LocalDate newEndDate) {
        this.newEndDate = newEndDate;
    }
}
