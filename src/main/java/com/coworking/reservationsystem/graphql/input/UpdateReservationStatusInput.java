package com.coworking.reservationsystem.graphql.input;

import com.coworking.reservationsystem.model.ReservationStatus;

public class UpdateReservationStatusInput {
    private String reservationId;
    private ReservationStatus status;
    
    // Constructors
    public UpdateReservationStatusInput() {}
    
    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}