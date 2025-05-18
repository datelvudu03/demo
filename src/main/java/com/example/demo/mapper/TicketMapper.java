package com.example.demo.mapper;

import com.example.demo.entity.TicketEntity;
import com.example.demo.gen.model.Ticket;
import org.mapstruct.Mapper;

@Mapper
public interface TicketMapper {
    Ticket mapTicketEntityToTicket(TicketEntity ticketEntity);
}
