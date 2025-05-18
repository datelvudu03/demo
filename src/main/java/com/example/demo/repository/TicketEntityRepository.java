package com.example.demo.repository;

import com.example.demo.entity.TicketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, Integer> {

    Optional<TicketEntity> findFirstByOrderByPositionAsc();

    default TicketEntity findFirstTicketByOrderByPositionAsc() {
        return findFirstByOrderByPositionAsc().orElse(null);
    }

    /**
     * Shifts all tickets after the given position one step forward in the queue.
     * Used after deleting a ticket (usually the one at position 0) to keep the queue continuous
     * without gaps in positions.
     *
     * @param removedPosition the position that was just removed; all tickets after this
     *                        will have their position decreased by 1
     */

    @Transactional
    @Modifying
    @Query("UPDATE TicketEntity t SET t.position = t.position - 1 WHERE t.position > :removedPosition")
    void shiftPositionsAfter(int removedPosition);
}
