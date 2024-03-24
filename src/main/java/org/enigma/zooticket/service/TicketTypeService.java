package org.enigma.zooticket.service;

import org.enigma.zooticket.constant.ETicketType;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.request.TicketTypeRequest;
import org.enigma.zooticket.model.response.TicketTypeResponse;

public interface TicketTypeService {
    TicketTypeResponse create(TicketTypeRequest ticketType);

    TicketTypeResponse update(TicketTypeRequest ticketType);

    TicketType getById(String id);

    TicketType getByTicketType(ETicketType ticketType);

    void delete(String id);
}
