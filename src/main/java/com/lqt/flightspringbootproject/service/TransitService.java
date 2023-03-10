package com.lqt.flightspringbootproject.service;


import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.dto.TransitDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Transit;

import java.util.List;

public interface TransitService {
    Transit save(TransitDto transitDto);

    List<Transit> findAll();

    Transit update(TransitDto transitDto);

    void deleteById(Long id);

    void enabledById(Long id);

    TransitDto getById(Long id);

    Transit findById(Long id);

    List<Transit> findAllByActivated();
}
