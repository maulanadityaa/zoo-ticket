package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.constant.ETicketType;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.exception.ApplicationException;
import org.enigma.zooticket.model.request.TicketTypeRequest;
import org.enigma.zooticket.model.response.TicketTypeResponse;
import org.enigma.zooticket.repository.TicketTypeRepository;
import org.enigma.zooticket.service.TicketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public TicketTypeResponse create(TicketTypeRequest ticketTypeRequest) {
        TicketType ticketType = toTicketType(ticketTypeRequest);
        ticketTypeRepository.saveTicketType(ticketType);

        return toTicketTypeResponse(ticketType);
    }

    @Override
    public TicketTypeResponse update(TicketTypeRequest ticketTypeRequest) {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeRequest.getId()).orElse(null);

        if (ticketType != null) {
            ticketType = TicketType.builder()
                    .id(ticketTypeRequest.getId())
                    .ticketType(ticketTypeRequest.getTicketType())
                    .price(ticketTypeRequest.getPrice())
                    .status(EStatus.ACTIVE)
                    .build();
            ticketTypeRepository.updateTicketType(ticketType);

            return toTicketTypeResponse(ticketType);
        }
        throw new ApplicationException("Ticket Type not found", String.format("Ticket type with id=%s", ticketTypeRequest.getId()), HttpStatus.NOT_FOUND);
    }

    @Override
    public TicketType getByTicketType(ETicketType ticketType) {
        TicketType type = ticketTypeRepository.findByTicketType(ticketType);

        if (type != null) {
            return type;
        }
        throw new ApplicationException("Ticket Type not found", String.format("Ticket type with id=%s", ticketType.toString()), HttpStatus.NOT_FOUND);
    }

    @Override
    public TicketType getById(String id) {
        return ticketTypeRepository.findById(id).orElseThrow(() -> new ApplicationException("Ticket Type not found", String.format("Ticket type with id=%s", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(String id) {
        TicketType ticketType = getById(id);

        ticketType.setStatus(EStatus.INACTIVE);
        ticketTypeRepository.updateTicketType(ticketType);
    }

    private static TicketTypeResponse toTicketTypeResponse(TicketType ticketType) {
        return TicketTypeResponse.builder()
                .id(ticketType.getId())
                .ticketType(ticketType.getTicketType())
                .price(ticketType.getPrice())
                .status(ticketType.getStatus())
                .build();
    }

    private static TicketType toTicketType(TicketTypeRequest ticketTypeRequest) {
        return TicketType.builder()
                .id(UUID.randomUUID().toString())
                .ticketType(ticketTypeRequest.getTicketType())
                .price(ticketTypeRequest.getPrice())
                .status(EStatus.ACTIVE)
                .build();
    }
}
