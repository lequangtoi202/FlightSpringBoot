package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.CustomerDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.repository.AirplaneRepository;
import com.lqt.flightspringbootproject.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(CustomerDto customerDto) {
        try{
            Customer customer = new Customer();
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setPhoneNum(customerDto.getPhoneNum());
            customer.setDob(customerDto.getDob());
            customer.setAddress(customerDto.getAddress());
            customer.set_activated(true);
            customer.set_deleted(false);
            return customerRepository.save(customer);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(CustomerDto customerDto) {
        try{
            Customer customer = customerRepository.getById(customerDto.getId());
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setPhoneNum(customerDto.getPhoneNum());
            customer.setDob(customerDto.getDob());
            customer.setAddress(customer.getAddress());
            return customerRepository.save(customer);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = customerRepository.getById(id);
        customer.set_deleted(true);
        customer.set_activated(false);
        customerRepository.save(customer);
    }

    @Override
    public void enabledById(Long id) {
        Customer customer = customerRepository.getById(id);
        customer.set_deleted(false);
        customer.set_activated(true);
        customerRepository.save(customer);
    }

    @Override
    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.getById(id);
        return mapToDto(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> findAllByActivated() {
        return customerRepository.findAllByActivated();
    }

    public CustomerDto mapToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhoneNum(customer.setPhoneNum(););
        customerDto.setDob(customer.setDob(););
        customerDto.set_activated(customer.is_activated());
        customerDto.set_deleted(customer.is_deleted());
        return customerDto;
    }

    private List<CustomerDto> mapListToDto(List<Customer> customers){
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customers){
            CustomerDto customerDto = mapToDto(customer);

            customerDtoList.add(customerDto);
        }

        return customerDtoList;
    }
}
