package org.enigma.zooticket.repository;

import org.enigma.zooticket.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
