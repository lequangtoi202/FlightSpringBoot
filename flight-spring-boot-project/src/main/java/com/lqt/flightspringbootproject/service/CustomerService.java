package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.CustomerDto;
import com.lqt.flightspringbootproject.dto.FlightDto;
import com.lqt.flightspringbootproject.dto.UserDto;
import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.model.Flight;
import com.lqt.flightspringbootproject.model.User;


public interface CustomerService {
    Customer save(CustomerDto customerDto);

    List<Customer > findAll();

    Customer  update(CustomerDto customerDto);

    void deleteById(Long id);

    void enabledById(Long id);

    CustomerDto getById(Long id);

    Customer findById(Long id);

    List<Customer> findAllByActivated();
}
