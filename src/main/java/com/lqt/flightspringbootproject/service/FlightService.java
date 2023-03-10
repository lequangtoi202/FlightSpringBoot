package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.FlightDto;
import com.lqt.flightspringbootproject.dto.FlightResponse;
import com.lqt.flightspringbootproject.dto.FlightSeatResponse;
import com.lqt.flightspringbootproject.model.Flight;

import java.util.Date;
import java.util.List;

public interface FlightService {
    Flight save(FlightDto flightDto);

    List<Flight> findAll();

    Flight update(FlightDto flightDto);

    void deleteById(Long id);

    void enabledById(Long id);

    FlightDto getById(Long id);

    Flight findById(Long id);

    List<Flight> findAllByActivated();

    List<FlightResponse> getFlights(String departure, String destination, Date date);

    FlightResponse getFlightByIdAndIsActivated(Long id);

    FlightSeatResponse getFlightSeat(Long id);

    int getRemainingSeat(Long id, int seat_class);

    Long getAirplaneIdByFlightId(Long id);
}
