package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.AirportDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.repository.AirportRepository;
import com.lqt.flightspringbootproject.service.AirportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    public final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Airport save(AirportDto airportDto) {
        try{
            Airport airport = new Airport();
            airport.setName(airportDto.getName());
            airport.setLocation(airportDto.getLocation());
            airport.set_activated(true);
            airport.set_deleted(false);
            return airportRepository.save(airport);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport update(AirportDto airportDto) {
        try{
            Airport airport = airportRepository.getById(airportDto.getId());
            airport.setName(airportDto.getName());
            airport.setLocation(airportDto.getLocation());
            return airportRepository.save(airport);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Airport airport = airportRepository.getById(id);
        airport.set_deleted(true);
        airport.set_activated(false);
        airportRepository.save(airport);
    }

    @Override
    public void enabledById(Long id) {
        Airport airport = airportRepository.getById(id);
        airport.set_deleted(false);
        airport.set_activated(true);
        airportRepository.save(airport);
    }

    @Override
    public AirportDto getById(Long id) {
        Airport airport = airportRepository.getById(id);
        return mapToDto(airport);
    }

    @Override
    public Airport findById(Long id) {
        return airportRepository.findById(id).get();
    }

    @Override
    public List<Airport> findAllByActivated() {
        return airportRepository.findAllByActivated();
    }

    public AirportDto mapToDto(Airport airport){
        AirportDto airportDto = new AirportDto();
        airportDto.setId(airport.getId());
        airportDto.setName(airport.getName());
        airportDto.setLocation(airport.getLocation());
        airportDto.set_activated(airport.is_activated());
        airportDto.set_deleted(airport.is_deleted());
        return airportDto;
    }

    private List<AirportDto> mapListToDto(List<Airport> airports){
        List<AirportDto> airportDtos = new ArrayList<>();
        for (Airport airport : airports){
            AirportDto airportDto = mapToDto(airport);
            airportDtos.add(airportDto);
        }

        return airportDtos;
    }
}
