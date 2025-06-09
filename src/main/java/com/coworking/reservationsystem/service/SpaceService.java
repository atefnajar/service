package com.coworking.reservationsystem.service;

import com.coworking.reservationsystem.model.Space;
import com.coworking.reservationsystem.model.SpaceType;
import com.coworking.reservationsystem.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {
    
    @Autowired
    private SpaceRepository spaceRepository;
    
    public List<Space> getAllSpaces() {
        return spaceRepository.findByIsActiveTrue();
    }
    
    public Optional<Space> getSpaceById(String id) {
        return spaceRepository.findById(id);
    }
    
    public List<Space> getAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        return spaceRepository.findAvailableSpaces(startTime, endTime);
    }
    
    public Space createSpace(String name, SpaceType type, Integer capacity, 
                           String description, List<String> amenities, Double hourlyRate) {
        Space space = new Space(name, type, capacity, description, amenities, hourlyRate);
        return spaceRepository.save(space);
    }
    
    public Space updateSpace(String id, String name, SpaceType type, Integer capacity,
                           String description, List<String> amenities, Double hourlyRate) {
        Space space = spaceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Space not found"));
            
        space.setName(name);
        space.setType(type);
        space.setCapacity(capacity);
        space.setDescription(description);
        space.setAmenities(amenities);
        space.setHourlyRate(hourlyRate);
        
        return spaceRepository.save(space);
    }
    
    public boolean deleteSpace(String id) {
        Space space = spaceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Space not found"));
            
        space.setActive(false);
        spaceRepository.save(space);
        return true;
    }
}