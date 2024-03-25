package org.enigma.zooticket.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String id;
    private String customerId;
    private List<TransactionDetailRequest> transactionDetails;
}
