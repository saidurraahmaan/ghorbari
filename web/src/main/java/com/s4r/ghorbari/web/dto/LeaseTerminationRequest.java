package com.s4r.ghorbari.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaseTerminationRequest {

    @NotNull(message = "Termination date is required")
    private LocalDate terminationDate;

    private String reason;

    // Constructors
    public LeaseTerminationRequest() {
    }

    // Getters and Setters
    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
