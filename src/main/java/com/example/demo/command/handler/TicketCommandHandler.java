package com.example.demo.command.handler;

import com.example.demo.command.base.CommandHandlerClass;
import com.example.demo.command.base.HandlesCommand;
import com.example.demo.command.cmd.CreateTicketCommand;
import com.example.demo.entity.TicketEntity;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.repository.TicketEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@CommandHandlerClass
@Component
@RequiredArgsConstructor
@Slf4j
public class TicketCommandHandler {

    private final TicketEntityRepository repository;
    private final TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

    @HandlesCommand()
    public void handleCreateUser(CreateTicketCommand cmd){
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
        //return ticketMapper.mapTicketEntityToTicket(ticket);
    }

}
