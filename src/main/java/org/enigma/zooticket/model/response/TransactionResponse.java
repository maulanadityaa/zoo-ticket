package org.enigma.zooticket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String id;
    private CustomerResponse customerResponse;
    private LocalDateTime transactionDate;
    private List<TransactionDetailResponse> transactionDetails;
}
