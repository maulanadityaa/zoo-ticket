package org.enigma.zooticket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.model.entity.TicketType;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String id;
    private Integer stock;
    private String validAt;
    private TicketType ticketType;
}
