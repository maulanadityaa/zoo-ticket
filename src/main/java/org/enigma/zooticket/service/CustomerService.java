package org.enigma.zooticket.service;

import org.enigma.zooticket.model.request.CustomerRequest;
import org.enigma.zooticket.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest);
    CustomerResponse update(CustomerRequest customerRequest);
    CustomerResponse findById(String id);
    List<CustomerResponse> findAll();
    List<CustomerResponse> findAllWhereActive();
    void delete(String id);
}
