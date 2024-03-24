package org.enigma.zooticket.repository;

import org.enigma.zooticket.constant.ETicketType;
import org.enigma.zooticket.model.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, String> {

    @Query("SELECT t FROM TicketType t WHERE t.ticketType = :ticketType AND t.status = 'ACTIVE'")
    TicketType findByTicketType(ETicketType ticketType);
}
