package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.dto.AirportDto;
import com.lqt.flightspringbootproject.dto.UserDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.model.User;

import java.util.List;


public interface AirRouteService {
    AirRoute save(AirRouteDto airRouteDto);

    List<AirRoute> findAll();

    AirRoute update(AirRouteDto airRouteDto);

    void deleteById(Long id);

    void enabledById(Long id);

    AirRouteDto getById(Long id);

    AirRoute findById(Long id);

    List<AirRoute> findAllByActivated();

    Long getAirRouteIdByDepartAndDes(String depart, String des);
}
