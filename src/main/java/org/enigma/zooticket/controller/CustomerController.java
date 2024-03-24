package org.enigma.zooticket.controller;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.request.CustomerRequest;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.CustomerResponse;
import org.enigma.zooticket.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.create(customerRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Customer created successfully")
                        .data(customerResponse)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id){
        CustomerResponse customerResponse = customerService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
              .body(CommonResponse.<CustomerResponse>builder()
                      .statusCode(HttpStatus.OK.value())
                      .message("Customer retrieved successfully")
                      .data(customerResponse)
                      .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer(){
        List<CustomerResponse> customerResponse = customerService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
            .body(CommonResponse.<List<CustomerResponse>>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("All customers retrieved successfully")
                    .data(customerResponse)
                    .build());
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.update(customerRequest);

        return ResponseEntity.status(HttpStatus.OK)
               .body(CommonResponse.<CustomerResponse>builder()
                       .statusCode(HttpStatus.OK.value())
                       .message("Customer updated successfully")
                       .data(customerResponse)
                       .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        customerService.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
              .body(CommonResponse.<String>builder()
                      .statusCode(HttpStatus.OK.value())
                      .message("Customer deleted successfully")
                      .build());
    }
}
