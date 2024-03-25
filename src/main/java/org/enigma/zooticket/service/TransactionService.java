package org.enigma.zooticket.service;

import org.enigma.zooticket.model.request.TransactionRequest;
import org.enigma.zooticket.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);

//    TransactionResponse updateTransaction(TransactionRequest transactionRequest);

    List<TransactionResponse> getAllTransactions();

    TransactionResponse getTransactionById(String id);
}
