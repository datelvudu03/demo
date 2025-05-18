package com.example.demo.repository;

import com.example.demo.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, Integer> {
}
