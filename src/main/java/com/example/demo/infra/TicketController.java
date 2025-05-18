package com.example.demo.infra;

import com.example.demo.gen.api.TicketsApi;
import com.example.demo.gen.model.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController implements TicketsApi {
    /**
     * POST /tickets : Generate a new ticket
     *
     * @return Ticket successfully created (status code 200)
     */
    @Override
    public ResponseEntity<Ticket> createTicket() {
        return null;
    }

    /**
     * GET /tickets/current : Get the current (oldest) active ticket
     *
     * @return Current ticket (status code 200)
     * or No ticket found (status code 404)
     */
    @Override
    public ResponseEntity<Ticket> getCurrentTicket() {
        return null;
    }

    /**
     * DELETE /tickets/current : Remove the current (oldest) active ticket
     *
     * @return Ticket removed successfully (status code 204)
     * or No ticket to remove (status code 404)
     */
    @Override
    public ResponseEntity<Void> removeCurrentTicket() {
        return null;
    }
}
