package com.coworking.reservationsystem.graphql.input;

import java.time.LocalDateTime;

public class CreateReservationInput {
    private String spaceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
    
    // Constructors
    public CreateReservationInput() {}
    
    // Getters and Setters
    public String getSpaceId() { return spaceId; }
    public void setSpaceId(String spaceId) { this.spaceId = spaceId; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}