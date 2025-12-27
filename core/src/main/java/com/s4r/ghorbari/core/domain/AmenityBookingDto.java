package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.AmenityBooking;

import java.time.LocalDateTime;

public class AmenityBookingDto {

    private Long id;
    private String bookingReference;
    private Long amenityId;
    private Long residentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AmenityBooking.BookingStatus status;
    private Integer numberOfGuests;
    private String purpose;
    private String specialRequests;
    private Long approvedById;
    private LocalDateTime approvedAt;
    private String rejectionReason;
    private LocalDateTime cancelledAt;
    private String cancellationReason;
    private String notes;

    // Constructors
    public AmenityBookingDto() {
    }

    public AmenityBookingDto(AmenityBooking booking) {
        this.id = booking.getId();
        this.bookingReference = booking.getBookingReference();
        this.amenityId = booking.getAmenityId();
        this.residentId = booking.getResidentId();
        this.startTime = booking.getStartTime();
        this.endTime = booking.getEndTime();
        this.status = booking.getStatus();
        this.numberOfGuests = booking.getNumberOfGuests();
        this.purpose = booking.getPurpose();
        this.specialRequests = booking.getSpecialRequests();
        this.approvedById = booking.getApprovedById();
        this.approvedAt = booking.getApprovedAt();
        this.rejectionReason = booking.getRejectionReason();
        this.cancelledAt = booking.getCancelledAt();
        this.cancellationReason = booking.getCancellationReason();
        this.notes = booking.getNotes();
    }

    public AmenityBooking toEntity() {
        AmenityBooking booking = new AmenityBooking();
        booking.setId(this.id);
        booking.setBookingReference(this.bookingReference);
        booking.setAmenityId(this.amenityId);
        booking.setResidentId(this.residentId);
        booking.setStartTime(this.startTime);
        booking.setEndTime(this.endTime);
        booking.setStatus(this.status);
        booking.setNumberOfGuests(this.numberOfGuests);
        booking.setPurpose(this.purpose);
        booking.setSpecialRequests(this.specialRequests);
        booking.setApprovedById(this.approvedById);
        booking.setApprovedAt(this.approvedAt);
        booking.setRejectionReason(this.rejectionReason);
        booking.setCancelledAt(this.cancelledAt);
        booking.setCancellationReason(this.cancellationReason);
        booking.setNotes(this.notes);
        return booking;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Long getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Long amenityId) {
        this.amenityId = amenityId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public AmenityBooking.BookingStatus getStatus() {
        return status;
    }

    public void setStatus(AmenityBooking.BookingStatus status) {
        this.status = status;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public Long getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(Long approvedById) {
        this.approvedById = approvedById;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
