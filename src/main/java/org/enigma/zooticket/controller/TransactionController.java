package org.enigma.zooticket.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.AppPath;
import org.enigma.zooticket.model.request.TransactionRequest;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.TransactionResponse;
import org.enigma.zooticket.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.TRANSACTIONS)
@SecurityRequirement(name = "Bearer Authentication")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllTransactions() {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<TransactionResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("All transactions retrieved successfully")
                        .data(transactionResponses)
                        .build());
    }

    @GetMapping("/")
    public ResponseEntity<?> getTransactionsByCustomerId(@RequestParam("customer-id") String customerId) {
        List<TransactionResponse> transactionResponses = transactionService.getTransactionsByCustomerId(customerId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<TransactionResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("All transactions retrieved successfully")
                        .data(transactionResponses)
                        .build());
    }

    @GetMapping(AppPath.GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
