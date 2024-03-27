package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.model.entity.Customer;
import org.enigma.zooticket.model.entity.User;
import org.enigma.zooticket.model.exception.ApplicationException;
import org.enigma.zooticket.model.request.CustomerRequest;
import org.enigma.zooticket.model.response.CustomerResponse;
import org.enigma.zooticket.model.response.UserResponse;
import org.enigma.zooticket.repository.CustomerRepository;
import org.enigma.zooticket.service.CustomerService;
import org.enigma.zooticket.util.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        try {
            Customer customer = toCustomer(customerRequest);
            customerRepository.saveCustomer(customer);

            return toCustomerResponse(customer);
        } catch (ParseException e) {
            throw new ApplicationException("Cannot create customer", String.format("Cannot parse date=%s", customerRequest.getDateOfBirth()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId()).orElseThrow(() -> new ApplicationException("Customer not found", String.format("Customer with id=%s", customerRequest.getId()), HttpStatus.NOT_FOUND));

        try {
            customer = Customer.builder()
                    .id(customer.getId())
                    .fullName(customerRequest.getFullName())
                    .dob(Helper.stringToDate(customerRequest.getDateOfBirth()))
                    .email(customerRequest.getEmail())
                    .mobilePhone(customerRequest.getPhone())
                    .status(EStatus.ACTIVE)
                    .user(customer.getUser())
                    .build();
            customerRepository.updateCustomer(customer);

            customer.setUser(User.builder()
                    .id(customer.getUser().getId())
                    .username(customer.getUser().getUsername())
                    .role(customer.getUser().getRole())
                    .build());

            return toCustomerResponse(customer);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApplicationException("Cannot create customer", String.format("Cannot parse date=%s", customerRequest.getDateOfBirth()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public CustomerResponse findById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApplicationException("Customer not found", String.format("Customer with id=%s", id), HttpStatus.NOT_FOUND));

        return toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerServiceImpl::toCustomerResponse).toList();
    }

    @Override
    public void delete(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApplicationException("Customer not found", String.format("Customer with id=%s", id), HttpStatus.NOT_FOUND));

        customer.setStatus(EStatus.INACTIVE);
        customerRepository.updateCustomer(customer);
    }

    private static CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getMobilePhone())
                .dateOfBirth(Helper.dateToString(customer.getDob()))
                .userCredential(UserResponse.builder()
                        .id(customer.getUser().getId())
                        .username(customer.getUser().getUsername())
                        .role(customer.getUser().getRole().getName())
                        .build())
                .build();
    }

    private static Customer toCustomer(CustomerRequest customerRequest) throws ParseException {
        return Customer.builder()
                .id(UUID.randomUUID().toString())
                .fullName(customerRequest.getFullName())
                .email(customerRequest.getEmail())
                .dob(Helper.stringToDate(customerRequest.getDateOfBirth()))
                .mobilePhone(customerRequest.getPhone())
                .user(customerRequest.getUser())
                .status(EStatus.ACTIVE)
                .build();
    }
}
