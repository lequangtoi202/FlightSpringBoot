package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.TicketDto;
import com.lqt.flightspringbootproject.model.*;

import java.util.List;


public interface TicketService {
    Ticket save(TicketDto ticketDto);

    List<Ticket> findAll();

    List<Ticket> getAllByUserId(Long id);

    Ticket update(TicketDto ticketDto);

    TicketDto getById(Long id);

    Ticket findById(Long id);

    int numOfSeatTaken(Long flightId, int seat_class);

    Ticket createTicket(User user, Customer customer, int seat_class, Flight flight, Seat seat);
}
