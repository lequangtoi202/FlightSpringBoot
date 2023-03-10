package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.CustomerDto;
import com.lqt.flightspringbootproject.model.Customer;

import java.util.List;


public interface CustomerService {
    Customer save(CustomerDto customerDto);

    List<Customer> findAll();

    Customer  update(CustomerDto customerDto);

    CustomerDto getById(Long id);

    Customer findById(Long id);

    Customer getByUserId(Long userId);

    Customer getCustomerByCodePaper(String code);

    CustomerDto mapToDto(Customer customer);
}
