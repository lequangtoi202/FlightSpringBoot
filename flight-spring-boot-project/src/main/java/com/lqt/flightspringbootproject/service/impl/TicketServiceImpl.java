package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.AirportDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.repository.AirportRepository;
import com.lqt.flightspringbootproject.service.AirportService;
import org.springframework.stereotype.Service;

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
            ticket.setEmployee(ticketDto.getEmployee());
            ticket.setCustomer(ticketDto.getCustomer());
            ticket.setSeat(ticketDto.getSeat());
            ticket.setFlight(ticketDto.getFlight());
            ticket.set_activated(true);
            ticket.set_deleted(false);
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
    public Ticket update(TicketDto ticketDto) {
        try{
            ticket.setSeat_class(ticketDto.getSeat_class());
            ticket.setSold_time(ticketDto.getSold_time());
            ticket.setEmployee(ticketDto.getEmployee());
            ticket.setCustomer(ticketDto.getCustomer());
            ticket.setSeat(ticketDto.getSeat());
            ticket.setFlight(ticketDto.getFlight());
            return ticketRepository.save(ticket);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Ticket ticket = ticketRepository.getById(id);
        ticket.set_deleted(true);
        ticket.set_activated(false);
        ticketRepository.save(ticket);
    }

    @Override
    public void enabledById(Long id) {
        Ticket ticket = ticketRepository.getById(id);
        ticket.set_deleted(false);
        ticket.set_activated(true);
        ticket.save(ticket);
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
    public List<Ticket> findAllByActivated() {
        return ticketRepository.findAllByActivated();
    }

    public TicketDto mapToDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();
        ticket.setSeat_class(ticketDto.getSeat_class());
        ticket.setSold_time(ticketDto.getSold_time());
        ticket.setEmployee(ticketDto.getEmployee());
        ticket.setCustomer(ticketDto.getCustomer());
        ticket.setSeat(ticketDto.getSeat());
        ticket.setFlight(ticketDto.getFlight());
        ticketDto.set_activated(airport.is_activated());
        ticketDto.set_deleted(airport.is_deleted());
        return ticketDto;
    }

    private List<TicketDto> mapListToDto(List<Ticket> tickets){
        List<TicketDto> ticketDto = new ArrayList<>();
        for (Ticket ticket : tickets){
            TicketDto ticketDto = mapToDto(ticket);
            ticketDto.add(ticketDto);
        }

        return ticketDtos;
    }
}
