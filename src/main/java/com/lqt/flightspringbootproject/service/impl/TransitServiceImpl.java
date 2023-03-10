package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.dto.TransitDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Transit;
import com.lqt.flightspringbootproject.repository.TransitRepository;
import com.lqt.flightspringbootproject.service.TransitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransitServiceImpl implements TransitService {
    private final TransitRepository transitRepository;

    public TransitServiceImpl(TransitRepository transitRepository) {
        this.transitRepository = transitRepository;
    }

    @Override
    public Transit save(TransitDto transitDto) {
        try{
            Transit transit = new Transit();
            transit.setStop_minutes(transitDto.getStop_minutes());
            transit.setNote(transitDto.getNote());
            transit.setAirRoute(transitDto.getAirRoute());
            transit.setAirport(transitDto.getAirport());
            transit.set_activated(true);
            transit.set_deleted(false);
            return transitRepository.save(transit);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Transit> findAll() {
        return transitRepository.findAll();
    }

    @Override
    public Transit update(TransitDto transitDto) {
        try{
            Transit transit = transitRepository.getById(transitDto.getId());
            transit.setStop_minutes(transitDto.getStop_minutes());
            transit.setNote(transitDto.getNote());
            transit.setAirport(transitDto.getAirport());
            transit.setAirRoute(transitDto.getAirRoute());
            return transitRepository.save(transit);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Transit transit = transitRepository.getById(id);
        transit.set_deleted(true);
        transit.set_activated(false);
        transitRepository.save(transit);
    }

    @Override
    public void enabledById(Long id) {
        Transit transit = transitRepository.getById(id);
        transit.set_deleted(false);
        transit.set_activated(true);
        transitRepository.save(transit);
    }

    @Override
    public TransitDto getById(Long id) {
        Transit transit = transitRepository.getById(id);
        return mapToDto(transit);
    }

    @Override
    public Transit findById(Long id) {
        return transitRepository.findById(id).get();
    }

    @Override
    public List<Transit> findAllByActivated() {
        return transitRepository.findAllByActivated();
    }

    public TransitDto mapToDto(Transit transit){
        TransitDto transitDto = new TransitDto();
        transitDto.setId(transit.getId());
        transitDto.setStop_minutes(transit.getStop_minutes());
        transitDto.setNote(transit.getNote());
        transitDto.setAirRoute(transit.getAirRoute());
        transitDto.setAirport(transit.getAirport());
        transitDto.set_activated(transit.is_activated());
        transitDto.set_deleted(transit.is_deleted());
        return transitDto;
    }

    private List<TransitDto> mapListToDto(List<Transit> transits){
        List<TransitDto> transitDtos = new ArrayList<>();
        for (Transit transit : transits){
            TransitDto transitDto = mapToDto(transit);
            transitDtos.add(transitDto);
        }

        return transitDtos;
    }
}
