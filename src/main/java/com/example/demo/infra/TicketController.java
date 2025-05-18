package com.example.demo.infra;


import com.example.demo.gen.api.TicketsApi;
import com.example.demo.gen.model.Ticket;
import com.example.demo.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TicketController implements TicketsApi {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<Ticket> createTicket() {
        log.info("Called createTicket()");
        return ResponseEntity.ok(ticketService.generateTicket());
    }

    @Override
    public ResponseEntity<Ticket> getCurrentTicket() {
        log.info("Called getCurrentTicket()");
        Ticket ticket = ticketService.getCurrentTicket();
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> removeCurrentTicket() {
        log.info("Called removeCurrentTicket()");
        ticketService.removeCurrentTicket();
        return ResponseEntity.noContent().build();
    }
}
