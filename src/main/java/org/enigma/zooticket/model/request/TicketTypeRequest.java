package org.enigma.zooticket.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.constant.ETicketType;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeRequest {
    private String id;
    private Long price;
    private ETicketType ticketType;
}
