package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Type;

import java.util.List;

public interface AirplaneService {
    Airplane save(AirplaneDto airplaneDto);

    List<Airplane> findAll();

    Airplane update(AirplaneDto airplaneDto);

    void deleteById(Long id);

    void enabledById(Long id);

    AirplaneDto getById(Long id);

    Airplane findById(Long id);

    List<Airplane> findAllByActivated();

}
