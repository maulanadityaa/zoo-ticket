package org.enigma.zooticket.service;

import org.enigma.zooticket.model.request.TransactionRequest;
import org.enigma.zooticket.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    List<TransactionResponse> getAllTransactions();

    List<TransactionResponse> getTransactionsByCustomerId(String customerId);

    TransactionResponse getTransactionById(String id);
}
