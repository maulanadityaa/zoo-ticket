package org.enigma.zooticket.controller;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.request.TicketRequest;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.TicketResponse;
import org.enigma.zooticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTicket(@RequestBody TicketRequest ticketRequest) {
        TicketResponse ticketResponse = ticketService.createTicket(ticketRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<TicketResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Ticket created successfully")
                        .data(ticketResponse)
                        .build());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTicket(@RequestBody TicketRequest ticketRequest) {
        TicketResponse ticketResponse = ticketService.updateTicket(ticketRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<TicketResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Ticket updated successfully")
                        .data(ticketResponse)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        List<TicketResponse> ticketResponses = ticketService.getAllTickets();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<TicketResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("All tickets retrieved successfully")
                        .data(ticketResponses)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable String id) {
        TicketResponse ticketResponse = ticketService.getTicketById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<TicketResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Ticket retrieved successfully")
                        .data(ticketResponse)
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTicket(@PathVariable String id) {
        ticketService.deleteTicket(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Ticket deleted successfully")
                        .build());
    }
}
