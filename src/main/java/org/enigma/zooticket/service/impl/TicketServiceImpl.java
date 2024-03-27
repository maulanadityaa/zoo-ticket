package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.entity.Ticket;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.exception.ApplicationException;
import org.enigma.zooticket.model.request.TicketRequest;
import org.enigma.zooticket.model.response.TicketResponse;
import org.enigma.zooticket.model.response.TicketTypeResponse;
import org.enigma.zooticket.repository.TicketRepository;
import org.enigma.zooticket.service.TicketService;
import org.enigma.zooticket.service.TicketTypeService;
import org.enigma.zooticket.util.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketTypeService ticketTypeService;

    @Override
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        try {
            TicketType ticketType = ticketTypeService.getByTicketType(ticketRequest.getTicketType());
            Ticket ticket = toTicket(ticketRequest, ticketType);
            ticketRepository.saveTicket(ticket);

            return toTicketResponse(ticket);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApplicationException("Cannot create ticket", String.format("Cannot parse date=%s", ticketRequest.getValidAt()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public TicketResponse updateTicket(TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketRepository.findById(ticketRequest.getId()).orElse(null);

            if (ticket != null) {
                TicketType ticketType = ticketTypeService.getByTicketType(ticketRequest.getTicketType());

                ticket = Ticket.builder()
                        .id(ticketRequest.getId())
                        .stock(ticketRequest.getStock())
                        .validAt(Helper.stringToDate(ticketRequest.getValidAt()))
                        .ticketType(ticketType)
                        .build();
                ticketRepository.updateTicket(ticket);

                return toTicketResponse(ticket);
            }
            throw new ApplicationException("Ticket not found", String.format("Ticket with id=%s", ticketRequest.getId()), HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApplicationException("Cannot parse ticket", String.format("Cannot parse date=%s", ticketRequest.getValidAt()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream().map(TicketServiceImpl::toTicketResponse).toList();
    }

    @Override
    public TicketResponse getTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ApplicationException("Ticket not found", String.format("Ticket with id=%s", id), HttpStatus.NOT_FOUND));

        return toTicketResponse(ticket);
    }

    @Override
    public void deleteTicket(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ApplicationException("Ticket not found", String.format("Ticket with id=%s", id), HttpStatus.NOT_FOUND));
        ticketRepository.delete(ticket);
    }

    private static TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .stock(ticket.getStock())
                .validAt(Helper.dateToString(ticket.getValidAt()))
                .ticketType(TicketTypeResponse.builder()
                        .id(ticket.getTicketType().getId())
                        .ticketType(ticket.getTicketType().getTicketType())
                        .price(ticket.getTicketType().getPrice())
                        .status(ticket.getTicketType().getStatus())
                        .build())
                .build();
    }

    private static Ticket toTicket(TicketRequest ticketRequest, TicketType ticketType) throws ParseException {
        return Ticket.builder()
                .id(UUID.randomUUID().toString())
                .stock(ticketRequest.getStock())
                .validAt(Helper.stringToDate(ticketRequest.getValidAt()))
                .ticketType(ticketType)
                .build();
    }
}
