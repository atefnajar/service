package com.coworking.reservationsystem.repository;

import com.coworking.reservationsystem.model.Reservation;
import com.coworking.reservationsystem.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUserId(String userId);
    List<Reservation> findBySpaceId(String spaceId);
    List<Reservation> findByStatus(ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.space.id = :spaceId " +
           "AND DATE(r.startTime) = DATE(:date)")
    List<Reservation> findBySpaceIdAndDate(@Param("spaceId") String spaceId, 
                                          @Param("date") LocalDateTime date);
    
    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.space.id = :spaceId " +
           "AND r.status != 'CANCELLED' AND r.startTime < :endTime AND r.endTime > :startTime")
    boolean existsConflictingReservation(@Param("spaceId") String spaceId,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
                                       
    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.space.id = :spaceId " +
           "AND r.status != 'CANCELLED' AND r.startTime < :endTime AND r.endTime > :startTime " +
           "AND r.id != :excludeId")
    boolean existsConflictingReservationExcluding(@Param("spaceId") String spaceId,
                                                @Param("startTime") LocalDateTime startTime,
                                                @Param("endTime") LocalDateTime endTime,
                                                @Param("excludeId") String excludeId);
}