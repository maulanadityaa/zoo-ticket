package org.enigma.zooticket.controller;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.entity.TicketType;
import org.enigma.zooticket.model.request.TicketTypeRequest;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.TicketTypeResponse;
import org.enigma.zooticket.service.TicketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket-type")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @PostMapping
    public ResponseEntity<?> createTicketType(@RequestBody TicketTypeRequest ticketTypeRequest) {
        TicketTypeResponse ticketTypeResponse = ticketTypeService.create(ticketTypeRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<TicketTypeResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Ticket type created successfully")
                        .data(ticketTypeResponse)
                        .build());
    }

    @PutMapping
    public ResponseEntity<?> updateTicketType(@RequestBody TicketTypeRequest ticketTypeRequest) {
        TicketTypeResponse ticketTypeResponse = ticketTypeService.update(ticketTypeRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<TicketTypeResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Ticket type updated successfully")
                        .data(ticketTypeResponse)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketType(@PathVariable String id) {
        ticketTypeService.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Ticket type deleted successfully")
                        .build());
    }
}
