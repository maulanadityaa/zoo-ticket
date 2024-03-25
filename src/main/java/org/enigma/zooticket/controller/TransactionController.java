package org.enigma.zooticket.controller;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.request.TransactionRequest;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.TransactionResponse;
import org.enigma.zooticket.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Transaction created successfully")
                        .data(transactionResponse)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<TransactionResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("All transactions retrieved successfully")
                        .data(transactionResponses)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Transaction retrieved successfully")
                        .data(transactionResponse)
                        .build());
    }

}
