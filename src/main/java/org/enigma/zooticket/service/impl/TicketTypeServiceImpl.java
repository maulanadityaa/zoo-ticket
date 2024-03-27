package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.constant.ETicketType;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.request.TicketTypeRequest;
import org.enigma.zooticket.model.response.TicketTypeResponse;
import org.enigma.zooticket.repository.TicketTypeRepository;
import org.enigma.zooticket.service.TicketTypeService;
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
        return null;
    }

    @Override
    public TicketType getByTicketType(ETicketType ticketType) {
        return ticketTypeRepository.findByTicketType(ticketType);
    }

    @Override
    public TicketType getById(String id) {
        return ticketTypeRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElse(null);

        if (ticketType != null) {
            ticketType.setStatus(EStatus.INACTIVE);
            ticketTypeRepository.updateTicketType(ticketType);
        }
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
