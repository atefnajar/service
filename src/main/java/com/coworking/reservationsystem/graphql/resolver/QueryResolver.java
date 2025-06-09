package com.coworking.reservationsystem.graphql.resolver;

import com.coworking.reservationsystem.model.Reservation;
import com.coworking.reservationsystem.model.Space;
import com.coworking.reservationsystem.model.User;
import com.coworking.reservationsystem.service.ReservationService;
import com.coworking.reservationsystem.service.SpaceService;
import com.coworking.reservationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class QueryResolver {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SpaceService spaceService;
    
    @Autowired
    private ReservationService reservationService;
    
    // User Queries
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> users() {
        return userService.getAllUsers();
    }
    
    @QueryMapping
    public User me() {
        // TODO: Get current user from security context
        return null;
    }
    
    // Space Queries
    @QueryMapping
    public List<Space> spaces() {
        return spaceService.getAllSpaces();
    }
    
    @QueryMapping
    public Space space(@Argument String id) {
        return spaceService.getSpaceById(id).orElse(null);
    }
    
    @QueryMapping
    public List<Space> availableSpaces(@Argument LocalDateTime startTime, @Argument LocalDateTime endTime) {
        return spaceService.getAvailableSpaces(startTime, endTime);
    }
    
    // Reservation Queries
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Reservation> reservations() {
        return reservationService.getAllReservations();
    }
    
    @QueryMapping
    public List<Reservation> myReservations() {
        // TODO: Get current user from security context
        return null;
    }
    
    @QueryMapping
    public Reservation reservation(@Argument String id) {
        return reservationService.getReservationById(id).orElse(null);
    }
    
    @QueryMapping
    public List<Reservation> spaceReservations(@Argument String spaceId, @Argument String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date + "T00:00:00");
        return reservationService.getSpaceReservations(spaceId, dateTime);
    }
}