package com.coworking.reservationsystem.graphql.input;

import com.coworking.reservationsystem.model.SpaceType;
import java.util.List;

public class CreateSpaceInput {
    private String name;
    private SpaceType type;
    private Integer capacity;
    private String description;
    private List<String> amenities;
    private Double hourlyRate;
    
    // Constructors
    public CreateSpaceInput() {}
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public SpaceType getType() { return type; }
    public void setType(SpaceType type) { this.type = type; }
    
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
    
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
}