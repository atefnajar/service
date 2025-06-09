package com.coworking.reservationsystem.graphql.resolver;

import com.coworking.reservationsystem.graphql.input.*;
import com.coworking.reservationsystem.graphql.type.AuthPayload;
import com.coworking.reservationsystem.model.*;
import com.coworking.reservationsystem.service.ReservationService;
import com.coworking.reservationsystem.service.SpaceService;
import com.coworking.reservationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class MutationResolver {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SpaceService spaceService;
    
    @Autowired
    private ReservationService reservationService;
    
    // Auth Mutations
    @MutationMapping
    public AuthPayload register(@Argument CreateUserInput input) {
        User user = userService.createUser(input.getEmail(), input.getPassword(), 
                                         input.getName(), UserRole.USER);
        String token = userService.authenticate(input.getEmail(), input.getPassword());
        return new AuthPayload(token, user);
    }
    
    @MutationMapping
    public AuthPayload login(@Argument LoginInput input) {
        String token = userService.authenticate(input.getEmail(), input.getPassword());
        User user = userService.getUserByEmail(input.getEmail()).orElse(null);
        return new AuthPayload(token, user);
    }
    
    // Space Mutations
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Space createSpace(@Argument CreateSpaceInput input) {
        return spaceService.createSpace(input.getName(), input.getType(), input.getCapacity(),
                                      input.getDescription(), input.getAmenities(), input.getHourlyRate());
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Space updateSpace(@Argument String id, @Argument CreateSpaceInput input) {
        return spaceService.updateSpace(id, input.getName(), input.getType(), input.getCapacity(),
                                      input.getDescription(), input.getAmenities(), input.getHourlyRate());
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteSpace(@Argument String id) {
        return spaceService.deleteSpace(id);
    }
    
    // Reservation Mutations
    @MutationMapping
    public Reservation createReservation(@Argument CreateReservationInput input) {
        // TODO: Get current user from security context
        User currentUser = userService.getAllUsers().get(0); // Temporary
        return reservationService.createReservation(currentUser, input.getSpaceId(),
                                                  input.getStartTime(), input.getEndTime(), input.getNotes());
    }
    
    @MutationMapping
    public Reservation updateReservationStatus(@Argument UpdateReservationStatusInput input) {
        return reservationService.updateReservationStatus(input.getReservationId(), input.getStatus());
    }
    
    @MutationMapping
    public Reservation cancelReservation(@Argument String id) {
        return reservationService.cancelReservation(id);
    }
}