package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_ticket (id, stock, valid_at, ticket_type_id) VALUES (:#{#ticket.id}, :#{#ticket.stock}, :#{#ticket.validAt}, :#{#ticket.ticketType.id});", nativeQuery = true)
    void saveTicket(Ticket ticket);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_ticket SET stock = :#{#ticket.stock}, valid_at = :#{#ticket.validAt}, ticket_type_id = :#{#ticket.ticketType.id} WHERE id = :#{#ticket.id}", nativeQuery = true)
    void updateTicket(Ticket ticket);
}
