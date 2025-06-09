package com.coworking.reservationsystem.config;

import com.coworking.reservationsystem.model.*;
import com.coworking.reservationsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
    
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SpaceRepository spaceRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Créer les utilisateurs par défaut
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@coworking.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setName("Admin User");
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
            
            User user = new User();
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setName("John Doe");
            user.setRole(UserRole.USER);
            userRepository.save(user);
            
            System.out.println("✅ Utilisateurs par défaut créés");
        }
        
        // Créer les espaces par défaut
        if (spaceRepository.count() == 0) {
            Space desk1 = new Space("Hot Desk #1", SpaceType.DESK, 1, 
                "Comfortable desk in open workspace", 
                Arrays.asList("WiFi", "Power Outlet", "Monitor"), 15.0);
            spaceRepository.save(desk1);
            
            Space desk2 = new Space("Hot Desk #2", SpaceType.DESK, 1, 
                "Standing desk with city view", 
                Arrays.asList("WiFi", "Power Outlet", "Standing Desk"), 18.0);
            spaceRepository.save(desk2);
            
            Space meetingRoom1 = new Space("Meeting Room Alpha", SpaceType.MEETING_ROOM, 6, 
                "Professional meeting room with whiteboard", 
                Arrays.asList("WiFi", "Projector", "Whiteboard", "Conference Phone"), 45.0);
            spaceRepository.save(meetingRoom1);
            
            Space meetingRoom2 = new Space("Meeting Room Beta", SpaceType.MEETING_ROOM, 10, 
                "Large meeting room with video conferencing", 
                Arrays.asList("WiFi", "Large Display", "Video Conferencing", "Whiteboard"), 65.0);
            spaceRepository.save(meetingRoom2);
            
            Space office = new Space("Private Office #1", SpaceType.PRIVATE_OFFICE, 4, 
                "Private office space for small teams", 
                Arrays.asList("WiFi", "Private Entrance", "Storage", "Phone Line"), 80.0);
            spaceRepository.save(office);
            
            System.out.println("✅ Espaces par défaut créés");
        }
        
        // Créer une réservation d'exemple
        if (reservationRepository.count() == 0) {
            User user = userRepository.findByEmail("user@example.com").orElse(null);
            Space space = spaceRepository.findAll().get(0);
            
            if (user != null && space != null) {
                Reservation reservation = new Reservation(user, space, 
                    LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
                    LocalDateTime.now().plusDays(1).withHour(12).withMinute(0),
                    "Working on client presentation");
                reservationRepository.save(reservation);
                
                System.out.println("✅ Réservation d'exemple créée");
            }
        }
        
        System.out.println("🚀 Application prête ! Accédez à GraphiQL : http://localhost:8080/graphiql");
    }
}