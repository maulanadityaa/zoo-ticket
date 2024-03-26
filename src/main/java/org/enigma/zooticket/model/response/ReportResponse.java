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
public class ReportResponse {
    private String customerId;
    private LocalDateTime transDate;
    private Long totalPrice;
    private Integer totalTicket;
}
