package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.model.entity.Customer;
import org.enigma.zooticket.model.request.CustomerRequest;
import org.enigma.zooticket.model.response.CustomerResponse;
import org.enigma.zooticket.repository.CustomerRepository;
import org.enigma.zooticket.service.CustomerService;
import org.enigma.zooticket.util.Helper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        System.out.println(customerRequest.getFullName());
        try {
            Customer customer = toCustomer(customerRequest);
            customerRepository.save(customer);

            return toCustomerResponse(customer);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId()).orElse(null);

        try {
            if (customer != null) {
                customer = Customer.builder()
                        .id(customer.getId())
                        .fullName(customerRequest.getFullName())
                        .dob(Helper.stringToDate(customerRequest.getDateOfBirth()))
                        .email(customerRequest.getEmail())
                        .mobilePhone(customerRequest.getPhone())
                        .status(EStatus.ACTIVE)
                        .user(customer.getUser())
                        .build();
                customerRepository.save(customer);

                return toCustomerResponse(customer);
            }
        } catch (ParseException e) {
            e.printStackTrace();
//            return null;
        }
        return null;
    }

    @Override
    public CustomerResponse findById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer != null) {
            return toCustomerResponse(customer);
        }
        return null;
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerServiceImpl::toCustomerResponse).toList();
    }

    @Override
    public void delete(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer != null) {
            customer.setStatus(EStatus.INACTIVE);
            customerRepository.save(customer);
        }
    }

    private static CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getMobilePhone())
                .dateOfBirth(Helper.dateToString(customer.getDob()))
                .userCredential(customer.getUser())
                .build();
    }

    private static Customer toCustomer(CustomerRequest customerRequest) throws ParseException {
        System.out.println(customerRequest.getDateOfBirth());
        System.out.println(customerRequest.getPhone());
        System.out.println(customerRequest.getFullName());
        return Customer.builder()
                .fullName(customerRequest.getFullName())
                .email(customerRequest.getEmail())
                .dob(Helper.stringToDate(customerRequest.getDateOfBirth()))
                .mobilePhone(customerRequest.getPhone())
                .user(customerRequest.getUser())
                .status(EStatus.ACTIVE)
                .build();
    }
}
