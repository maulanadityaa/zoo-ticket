package org.enigma.zooticket.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.entity.Customer;
import org.enigma.zooticket.model.entity.Role;
import org.enigma.zooticket.model.entity.Ticket;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.entity.Transaction;
import org.enigma.zooticket.model.entity.TransactionDetail;
import org.enigma.zooticket.model.entity.User;
import org.enigma.zooticket.model.exception.ApplicationException;
import org.enigma.zooticket.model.request.TicketRequest;
import org.enigma.zooticket.model.request.TransactionRequest;
import org.enigma.zooticket.model.response.CustomerResponse;
import org.enigma.zooticket.model.response.TicketResponse;
import org.enigma.zooticket.model.response.TicketTypeResponse;
import org.enigma.zooticket.model.response.TransactionDetailResponse;
import org.enigma.zooticket.model.response.TransactionResponse;
import org.enigma.zooticket.model.response.UserResponse;
import org.enigma.zooticket.repository.TransactionRepository;
import org.enigma.zooticket.service.CustomerService;
import org.enigma.zooticket.service.TicketService;
import org.enigma.zooticket.service.TransactionService;
import org.enigma.zooticket.util.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final TicketService ticketService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        try {
            CustomerResponse customerResponse = customerService.findById(transactionRequest.getCustomerId());
            Customer customer = Customer.builder()
                    .id(customerResponse.getId())
                    .fullName(customerResponse.getFullName())
                    .email(customerResponse.getEmail())
                    .dob(Helper.stringToDate(customerResponse.getDateOfBirth()))
                    .mobilePhone(customerResponse.getPhone())
                    .user(User.builder()
                            .id(customerResponse.getUserCredential().getId())
                            .username(customerResponse.getUserCredential().getUsername())
                            .role(Role.builder()
                                    .name(customerResponse.getUserCredential().getRole())
                                    .build())
                            .build())
                    .build();

            List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails().stream().map(transactionDetailRequest -> {
                TicketResponse ticketResponse = ticketService.getTicketById(transactionDetailRequest.getTicketId());
                Ticket ticket = null;
                try {
                    ticket = Ticket.builder()
                            .id(ticketResponse.getId())
                            .stock(ticketResponse.getStock())
                            .ticketType(TicketType.builder()
                                    .id(ticketResponse.getTicketType().getId())
                                    .price(ticketResponse.getTicketType().getPrice())
                                    .status(ticketResponse.getTicketType().getStatus())
                                    .ticketType(ticketResponse.getTicketType().getTicketType())
                                    .build())
                            .validAt(Helper.stringToDate(ticketResponse.getValidAt()))
                            .build();
                } catch (ParseException e) {
                    throw new ApplicationException("Cannot parse ticket", String.format("Cannot parse date=%s", ticketResponse.getValidAt()), HttpStatus.BAD_REQUEST);
                }

                return TransactionDetail.builder()
                        .quantity(transactionDetailRequest.getQuantity())
                        .createdAt(LocalDateTime.now())
                        .ticket(ticket)
                        .build();
            }).toList();

            Transaction transaction = Transaction.builder()
                    .transDate(LocalDateTime.now())
                    .customer(customer)
                    .transactionDetails(transactionDetails)
                    .build();
            transactionRepository.saveAndFlush(transaction);

            List<TransactionDetailResponse> transactionDetailResponses = transaction.getTransactionDetails().stream().map(transactionDetail -> {
                transactionDetail.setTransaction(transaction);

                Ticket ticket = transactionDetail.getTicket();
                ticket.setStock(ticket.getStock() - transactionDetail.getQuantity());
                TicketRequest ticketRequest = TicketRequest.builder()
                        .id(ticket.getId())
                        .ticketType(ticket.getTicketType().getTicketType())
                        .stock(ticket.getStock() - transactionDetail.getQuantity())
                        .validAt(Helper.dateToString(ticket.getValidAt()))
                        .build();
                ticketService.updateTicket(ticketRequest);

                return toTransactionDetailResponse(transactionDetail, ticket);
            }).toList();

            return toTransactionResponse(transaction, transactionDetailResponses);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApplicationException("Failed parse date", "Cannot parse date", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionResponse> transactionResponses = new ArrayList<>();

        for (Transaction transaction : transactions) {
            List<TransactionDetailResponse> transactionDetailResponses = toTransactionDetailResponseList(transaction);

            transactionResponses.add(toTransactionResponse(transaction, transactionDetailResponses));
        }
        return transactionResponses;
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ApplicationException("Transaction not found", String.format("Transaction with id=%s", id), HttpStatus.NOT_FOUND));

        List<TransactionDetailResponse> transactionDetailResponses = toTransactionDetailResponseList(transaction);

        return toTransactionResponse(transaction, transactionDetailResponses);
    }

    private static List<TransactionDetailResponse> toTransactionDetailResponseList(Transaction transaction) {
        return transaction.getTransactionDetails().stream().map(transactionDetail -> {
            Ticket ticket = transactionDetail.getTicket();

            return toTransactionDetailResponse(transactionDetail, ticket);
        }).toList();
    }

    @Override
    public List<TransactionResponse> getTransactionsByCustomerId(String customerId) {
        List<Transaction> transactionList = transactionRepository.findByCustomerId(customerId);
        List<TransactionResponse> transactionResponses = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            List<TransactionDetailResponse> transactionDetailResponses = toTransactionDetailResponseList(transaction);

            transactionResponses.add(toTransactionResponse(transaction, transactionDetailResponses));
        }
        return transactionResponses;
    }

    private static TransactionResponse toTransactionResponse(Transaction transaction, List<TransactionDetailResponse> transactionDetailResponses) {
        Long totalPrice = (long) transactionDetailResponses.stream().mapToInt(tdr -> Math.toIntExact(tdr.getTicketResponse().getTicketType().getPrice() * tdr.getQuantity())).sum();
        System.out.println(transaction.getTransDate().getMonthValue());
        System.out.println(transaction.getTransDate().getYear());

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionDate(transaction.getTransDate())
                .totalPrice(totalPrice)
                .customerResponse(CustomerResponse.builder()
                        .id(transaction.getCustomer().getId())
                        .fullName(transaction.getCustomer().getFullName())
                        .email(transaction.getCustomer().getEmail())
                        .phone(transaction.getCustomer().getMobilePhone())
                        .dateOfBirth(Helper.dateToString(transaction.getCustomer().getDob()))
                        .userCredential(UserResponse.builder()
                                .id(transaction.getCustomer().getUser().getId())
                                .username(transaction.getCustomer().getUser().getUsername())
                                .role(transaction.getCustomer().getUser().getRole().getName())
                                .build())
                        .build())
                .transactionDetails(transactionDetailResponses)
                .build();
    }

    private static TransactionDetailResponse toTransactionDetailResponse(TransactionDetail transactionDetail, Ticket ticket) {
        return TransactionDetailResponse.builder()
                .id(transactionDetail.getId())
                .quantity(transactionDetail.getQuantity())
                .createdAt(transactionDetail.getCreatedAt())
                .ticketResponse(TicketResponse.builder()
                        .id(ticket.getId())
                        .stock(ticket.getStock())
                        .validAt(Helper.dateToString(ticket.getValidAt()))
                        .ticketType(TicketTypeResponse.builder()
                                .id(ticket.getTicketType().getId())
                                .price(ticket.getTicketType().getPrice())
                                .ticketType(ticket.getTicketType().getTicketType())
                                .status(ticket.getTicketType().getStatus())
                                .build())
                        .build())
                .build();
    }
}
