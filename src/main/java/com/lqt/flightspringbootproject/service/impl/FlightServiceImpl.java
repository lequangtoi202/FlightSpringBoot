package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.FlightDto;
import com.lqt.flightspringbootproject.dto.FlightResponse;
import com.lqt.flightspringbootproject.dto.FlightSeatResponse;
import com.lqt.flightspringbootproject.model.Flight;
import com.lqt.flightspringbootproject.repository.FlightRepository;
import com.lqt.flightspringbootproject.service.AirRouteService;
import com.lqt.flightspringbootproject.service.FlightService;
import com.lqt.flightspringbootproject.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AirRouteService airRouteService;
    private final TicketService ticketService;

    public FlightServiceImpl(FlightRepository flightRepository, AirRouteService airRouteService, TicketService ticketService) {
        this.flightRepository = flightRepository;
        this.airRouteService = airRouteService;
        this.ticketService = ticketService;
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

    @Override
    public List<FlightResponse> getFlights(String departure, String destination, Date date) {
        try {
            Long airRouteId = airRouteService.getAirRouteIdByDepartAndDes(departure, destination);
            List<FlightResponse> flights = flightRepository.getFlightByAirRouteId(airRouteId, date);
            return flights;
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public FlightResponse getFlightByIdAndIsActivated(Long id) {
        return flightRepository.getFlightByIdAndIsActivated(id);
    }

    @Override
    public FlightSeatResponse getFlightSeat(Long id) {
        return flightRepository.getFlightSeat(id);
    }

    @Override
    public int getRemainingSeat(Long id, int seat_class) {
        FlightSeatResponse flightSeatResponse = flightRepository.getFlightSeat(id);
        if (seat_class == 1){
            return flightSeatResponse.getNum_of_f_seat();
        }
        else{
            return flightSeatResponse.getNum_of_s_seat();
        }
    }

    @Override
    public Long getAirplaneIdByFlightId(Long id) {
        return flightRepository.getAirplaneIdByFlight(id);
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
