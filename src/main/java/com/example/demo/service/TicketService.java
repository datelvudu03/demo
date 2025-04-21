package com.example.demo.service;

import com.example.demo.entity.TicketEntity;
import com.example.demo.gen.model.Ticket;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.repository.TicketEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketEntityRepository repository;
    private final TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

    public Ticket generateTicket() {
        log.info("Called generateTicket()");
        int maxPosition = repository.findAll().stream()
                .mapToInt(TicketEntity::getPosition)
                .max()
                .orElse(-1);

        TicketEntity ticket = new TicketEntity();
        ticket.setCreated(LocalDateTime.now());
        ticket.setPosition(maxPosition + 1);

        ticket = repository.save(ticket);

        log.info("Generated ticket {}", ticket);
        return ticketMapper.mapTicketEntityToTicket(ticket);
    }

    public Ticket getCurrentTicket() {
        log.info("Called getCurrentTicket()");
        return ticketMapper.mapTicketEntityToTicket(repository.findFirstTicketByOrderByPositionAsc());
    }

    @Transactional
    public void removeCurrentTicket() {
        log.info("Called removeCurrentTicket()");
        Optional<TicketEntity> currentOpt = repository.findFirstByOrderByPositionAsc();
        if (currentOpt.isPresent()) {
            TicketEntity current = currentOpt.get();
            repository.delete(current);
            repository.shiftPositionsAfter(current.getPosition());
            log.info("Removed ticket {}", current);
        } else {
            log.warn("No current ticket found");
        }
    }

}
