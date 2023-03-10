package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.TicketDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.repository.TicketRepository;
import com.lqt.flightspringbootproject.service.SeatService;
import com.lqt.flightspringbootproject.service.TicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    public final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(TicketDto ticketDto) {
        try{
            Ticket ticket = new Ticket();
            ticket.setSeat_class(ticketDto.getSeat_class());
            ticket.setSold_time(ticketDto.getSold_time());
            ticket.setCustomer(ticketDto.getCustomer());
            ticket.setSeat(ticketDto.getSeat());
            ticket.setFlight(ticketDto.getFlight());
            return ticketRepository.save(ticket);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getAllByUserId(Long id) {
        return ticketRepository.getAllByBuyerId(id);
    }

    @Override
    public Ticket update(TicketDto ticketDto) {
        try{
            Ticket ticket = ticketRepository.getById(ticketDto.getId());
            ticket.setSeat_class(ticketDto.getSeat_class());
            ticket.setSold_time(ticketDto.getSold_time());
            ticket.setCustomer(ticketDto.getCustomer());
            ticket.setSeat(ticketDto.getSeat());
            ticket.setFlight(ticketDto.getFlight());
            return ticketRepository.save(ticket);
        }catch (Exception e){
        }
        return null;
    }


    @Override
    public TicketDto getById(Long id) {
        Ticket ticket = ticketRepository.getById(id);
        return mapToDto(ticket);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).get();
    }

    @Override
    public int numOfSeatTaken(Long flightId, int seat_class) {
        return ticketRepository.countTicketBySeat_class(flightId, seat_class);
    }

    @Override
    public Ticket createTicket(User user, Customer customer, int seat_class, Flight flight, Seat seat) {
        Ticket ticket = new Ticket();
        ticket.setSeat(seat);
        ticket.setSeat_class(seat_class);
        ticket.setFlight(flight);
        ticket.setUser(user);
        ticket.setCustomer(customer);
        ticket.setSold_time(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }


    public TicketDto mapToDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();
        ticket.setSeat_class(ticketDto.getSeat_class());
        ticket.setSold_time(ticketDto.getSold_time());
        ticket.setCustomer(ticketDto.getCustomer());
        ticket.setSeat(ticketDto.getSeat());
        ticket.setFlight(ticketDto.getFlight());
        return ticketDto;
    }

    private List<TicketDto> mapListToDto(List<Ticket> tickets){
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets){
            TicketDto ticketDto = mapToDto(ticket);
            ticketDtos.add(ticketDto);
        }

        return ticketDtos;
    }
}
