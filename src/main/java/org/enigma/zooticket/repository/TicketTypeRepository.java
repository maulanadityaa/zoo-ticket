package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.constant.ETicketType;
import org.enigma.zooticket.model.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, String> {

    @Query(value = "SELECT * FROM m_ticket_type WHERE ticket_type = :#{#ticketType.name()} AND status = 'ACTIVE'", nativeQuery = true)
    TicketType findByTicketType(ETicketType ticketType);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_ticket_type (id, price, status, ticket_type) VALUES (:#{#ticketType.id}, :#{#ticketType.price}, :#{#ticketType.status.name()}, :#{#ticketType.ticketType.name()});", nativeQuery = true)
    void saveTicketType(TicketType ticketType);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_ticket_type SET ticket_type = :#{#ticketType.ticketType.name()}, price = :#{#ticketType.price}, status = :#{#ticketType.status.name()} WHERE id = :#{#ticketType.id}", nativeQuery = true)
    void updateTicketType(TicketType ticketType);
}
