package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.dto.FlightDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Flight;
import com.lqt.flightspringbootproject.repository.FlightRepository;
import com.lqt.flightspringbootproject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight save(FlightDto flightDto) {
        try{
            Flight flight = new Flight();
            flight.setAirplane(flightDto.getAirplane());
            flight.setAirRoute(flightDto.getAirRoute());
            flight.setTicketPrice(flightDto.getTicketPrice());
            flight.set_activated(true);
            flight.set_deleted(false);
            return flightRepository.save(flight);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight update(FlightDto flightDto) {
        try{
            Flight flight = flightRepository.getById(flightDto.getId());
            flight.setAirplane(flightDto.getAirplane());
            flight.setAirRoute(flightDto.getAirRoute());
            flight.setTicketPrice(flightDto.getTicketPrice());
            return flightRepository.save(flight);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Flight flight = flightRepository.getById(id);
        flight.set_deleted(true);
        flight.set_activated(false);
        flightRepository.save(flight);
    }

    @Override
    public void enabledById(Long id) {
        Flight flight = flightRepository.getById(id);
        flight.set_deleted(false);
        flight.set_activated(true);
        flightRepository.save(flight);
    }

    @Override
    public FlightDto getById(Long id) {
        Flight flight = flightRepository.getById(id);
        return mapToDto(flight);
    }

    @Override
    public Flight findById(Long id) {
        return flightRepository.findById(id).get();
    }

    @Override
    public List<Flight> findAllByActivated() {
        return flightRepository.getAllByActivated();
    }

    public FlightDto mapToDto(Flight flight){
        FlightDto flightDto = new FlightDto();
        flightDto.setId(flight.getId());
        flightDto.setAirplane(flight.getAirplane());
        flightDto.setAirRoute(flight.getAirRoute());
        flightDto.setTicketPrice(flight.getTicketPrice());
        flightDto.set_activated(flight.is_activated());
        flightDto.set_deleted(flight.is_deleted());
        return flightDto;
    }

    private List<FlightDto> mapListToDto(List<Flight> flights){
        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights){
            FlightDto flightDto = mapToDto(flight);
            flightDtos.add(flightDto);
        }

        return flightDtos;
    }
}
