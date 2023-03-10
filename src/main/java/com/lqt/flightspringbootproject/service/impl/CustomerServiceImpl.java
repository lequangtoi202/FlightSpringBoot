package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.CustomerDto;
import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.repository.CustomerRepository;
import com.lqt.flightspringbootproject.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
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
            customer.setUser(customerDto.getUser());
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
            customer.setUser(customerDto.getUser());
            return customerRepository.save(customer);
        }catch (Exception e){
        }
        return null;
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
    public Customer getByUserId(Long id) {
        return customerRepository.getByUser(id);
    }

    @Override
    public Customer getCustomerByCodePaper(String code) {
        return customerRepository.getCustomerByCode(code);
    }

    @Override
    public CustomerDto mapToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhoneNum(customer.getPhoneNum());
        customerDto.setDob(customer.getDob());
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
