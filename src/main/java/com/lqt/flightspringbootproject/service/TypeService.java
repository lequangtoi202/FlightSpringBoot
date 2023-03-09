package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.TypeDto;
import com.lqt.flightspringbootproject.model.Type;

import java.util.List;

public interface TypeService {
    Type save(TypeDto typeDto);

    List<Type> findAll();

    List<Type> findAllByActivated();

    Type findById(Long id);

    Type update(Type type);

    void deleteById(Long id);

    void enabledById(Long id);


}
