package org.enigma.zooticket.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.constant.ETicketType;
import org.enigma.zooticket.model.entity.TicketType;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    private String id;
    private Integer stock;
    private String validAt;
    private ETicketType ticketType;
}
