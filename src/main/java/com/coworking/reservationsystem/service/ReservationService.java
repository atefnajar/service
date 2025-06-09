package com.coworking.reservationsystem.service;

import com.coworking.reservationsystem.model.Reservation;
import com.coworking.reservationsystem.model.ReservationStatus;
import com.coworking.reservationsystem.model.Space;
import com.coworking.reservationsystem.model.User;
import com.coworking.reservationsystem.repository.ReservationRepository;
import com.coworking.reservationsystem.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private SpaceRepository spaceRepository;
    
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public List<Reservation> getUserReservations(String userId) {
        return reservationRepository.findByUserId(userId);
    }
    
    public Optional<Reservation> getReservationById(String id) {
        return reservationRepository.findById(id);
    }
    
    public List<Reservation> getSpaceReservations(String spaceId, LocalDateTime date) {
        return reservationRepository.findBySpaceIdAndDate(spaceId, date);
    }
    
    public Reservation createReservation(User user, String spaceId, LocalDateTime startTime,
                                       LocalDateTime endTime, String notes) {
        Space space = spaceRepository.findById(spaceId)
            .orElseThrow(() -> new RuntimeException("Space not found"));
            
        if (!space.getActive()) {
            throw new RuntimeException("Space is not active");
        }
        
        if (reservationRepository.existsConflictingReservation(spaceId, startTime, endTime)) {
            throw new RuntimeException("Time slot is already booked");
        }
        
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot book in the past");
        }
        
        if (endTime.isBefore(startTime.plusHours(1))) {
            throw new RuntimeException("Minimum booking duration is 1 hour");
        }
        
        Reservation reservation = new Reservation(user, space, startTime, endTime, notes);
        return reservationRepository.save(reservation);
    }
    
    public Reservation updateReservationStatus(String id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));
            
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }
    
    public Reservation cancelReservation(String id) {
        return updateReservationStatus(id, ReservationStatus.CANCELLED);
    }
}