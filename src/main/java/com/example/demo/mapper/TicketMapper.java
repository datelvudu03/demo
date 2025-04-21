package com.example.demo.mapper;

import com.example.demo.entity.TicketEntity;
import com.example.demo.gen.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TicketMapper {
    @Mapping(target = "number", source = "id")
    @Mapping(target = "timestamp", source = "created")
    Ticket mapTicketEntityToTicket(TicketEntity ticketEntity);
}
