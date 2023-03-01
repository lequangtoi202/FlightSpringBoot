package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.FlightDto;
import com.lqt.flightspringbootproject.model.Flight;

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
}
