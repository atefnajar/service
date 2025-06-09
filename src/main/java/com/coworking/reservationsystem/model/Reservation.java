package com.coworking.reservationsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    @NotNull
    private Space space;
    
    @Column(name = "start_time")
    @NotNull
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    @NotNull
    private LocalDateTime endTime;
    
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.CONFIRMED;
    
    private String notes;
    
    @Column(name = "total_cost")
    private Double totalCost;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors
    public Reservation() {}
    
    public Reservation(User user, Space space, LocalDateTime startTime, 
                      LocalDateTime endTime, String notes) {
        this.user = user;
        this.space = space;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        calculateTotalCost();
    }
    
    public void calculateTotalCost() {
        if (space != null && startTime != null && endTime != null) {
            long hours = java.time.Duration.between(startTime, endTime).toHours();
            this.totalCost = hours * space.getHourlyRate();
        }
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Space getSpace() { return space; }
    public void setSpace(Space space) { 
        this.space = space;
        calculateTotalCost();
    }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { 
        this.startTime = startTime;
        calculateTotalCost();
    }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { 
        this.endTime = endTime;
        calculateTotalCost();
    }
    
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public Double getTotalCost() { return totalCost; }
    public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}