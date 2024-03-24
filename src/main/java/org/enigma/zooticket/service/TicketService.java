package org.enigma.zooticket.service;

import org.enigma.zooticket.model.request.TicketRequest;
import org.enigma.zooticket.model.response.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(TicketRequest ticketRequest);

    TicketResponse updateTicket(TicketRequest ticketRequest);

    List<TicketResponse> getAllTickets();

    TicketResponse getTicketById(String id);

    void deleteTicket(String id);
}
