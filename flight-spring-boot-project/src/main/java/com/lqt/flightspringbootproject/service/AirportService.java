package com.lqt.flightspringbootproject.service;


import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.AirportDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Airport;

import java.util.List;

public interface AirportService {
    Airport save(AirportDto airportDto);

    List<Airport> findAll();

    Airport update(AirportDto airportDto);

    void deleteById(Long id);

    void enabledById(Long id);

    AirportDto getById(Long id);

    Airport findById(Long id);

    List<Airport> findAllByActivated();
}
