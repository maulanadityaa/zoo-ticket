package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.entity.Ticket;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.request.TicketRequest;
import org.enigma.zooticket.model.response.TicketResponse;
import org.enigma.zooticket.repository.TicketRepository;
import org.enigma.zooticket.service.TicketService;
import org.enigma.zooticket.service.TicketTypeService;
import org.enigma.zooticket.util.Helper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketTypeService ticketTypeService;

    @Override
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        try {
            TicketType ticketType = ticketTypeService.getByTicketType(ticketRequest.getTicketType().getTicketType());
            if (ticketType != null) {
                Ticket ticket = toTicket(ticketRequest, ticketType);
                ticketRepository.save(ticket);

                return toTicketResponse(ticket);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TicketResponse updateTicket(TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketRepository.findById(ticketRequest.getId()).orElse(null);

            if (ticket != null) {
                TicketType ticketType = ticketTypeService.getByTicketType(ticketRequest.getTicketType().getTicketType());

                if (ticketType != null) {
                    ticket = Ticket.builder()
                            .id(ticketRequest.getId())
                            .stock(ticketRequest.getStock())
                            .validAt(Helper.stringToDate(ticketRequest.getValidAt()))
                            .ticketType(ticketType)
                            .build();
                    ticketRepository.save(ticket);

                    return toTicketResponse(ticket);
                }
                return null;
            }
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream().map(TicketServiceImpl::toTicketResponse).toList();
    }

    @Override
    public TicketResponse getTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket != null) {
            return toTicketResponse(ticket);
        }
        return null;
    }

    @Override
    public void deleteTicket(String id) {
        ticketRepository.findById(id).ifPresent(ticket -> ticketRepository.delete(ticket));
    }

    private static TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .stock(ticket.getStock())
                .validAt(Helper.dateToString(ticket.getValidAt()))
                .ticketType(TicketType.builder()
                        .ticketType(ticket.getTicketType().getTicketType())
                        .price(ticket.getTicketType().getPrice())
                        .build())
                .build();
    }

    private static Ticket toTicket(TicketRequest ticketRequest, TicketType ticketType) throws ParseException {
        return Ticket.builder()
                .stock(ticketRequest.getStock())
                .validAt(Helper.stringToDate(ticketRequest.getValidAt()))
                .ticketType(ticketType)
                .build();
    }
}
