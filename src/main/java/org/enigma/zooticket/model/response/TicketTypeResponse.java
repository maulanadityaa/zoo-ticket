package org.enigma.zooticket.model.response;

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
public class TicketTypeResponse {
    private String id;
    private ETicketType ticketType;
    private Long price;
    private EStatus status;
}
