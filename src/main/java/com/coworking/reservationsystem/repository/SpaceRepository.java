package com.coworking.reservationsystem.repository;

import com.coworking.reservationsystem.model.Space;
import com.coworking.reservationsystem.model.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {
    List<Space> findByIsActiveTrue();
    List<Space> findByTypeAndIsActiveTrue(SpaceType type);
    
    @Query("SELECT s FROM Space s WHERE s.isActive = true AND s.id NOT IN " +
           "(SELECT r.space.id FROM Reservation r WHERE r.status != 'CANCELLED' " +
           "AND r.startTime < :endTime AND r.endTime > :startTime)")
    List<Space> findAvailableSpaces(@Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);
}