package org.enigma.zooticket.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.model.entity.Ticket;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailRequest {
    private String id;
    private Integer quantity;
    private String ticketId;
}
