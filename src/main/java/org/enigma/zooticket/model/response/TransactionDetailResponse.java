package org.enigma.zooticket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailResponse {
    private String id;
    private Integer quantity;
    private LocalDateTime createdAt;
    private TicketResponse ticketResponse;
}
