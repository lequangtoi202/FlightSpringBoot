package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.UserDto;
import com.lqt.flightspringbootproject.model.User;


public interface TicketService {
    Ticket save(TicketDto ticketDto);

    List<Ticket> findAll();

    Ticket update(TicketDto ticketDto);

    void deleteById(Long id);

    void enabledById(Long id);

    TicketDto getById(Long id);

    Ticket findById(Long id);

    List<Ticket> findAllByActivated();
}
