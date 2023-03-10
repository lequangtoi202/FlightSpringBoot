package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.repository.AirRouteRepository;
import com.lqt.flightspringbootproject.service.AirRouteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirRouteServiceImpl implements AirRouteService {
    private final AirRouteRepository airRouteRepository;

    public AirRouteServiceImpl(AirRouteRepository airRouteRepository) {
        this.airRouteRepository = airRouteRepository;
    }

    @Override
    public AirRoute save(AirRouteDto airRouteDto) {
        try{
            AirRoute airRoute = new AirRoute();
            airRoute.setName(airRouteDto.getName());
            airRoute.setDeparture(airRouteDto.getDeparture());
            airRoute.setDestination(airRouteDto.getDestination());
            airRoute.set_activated(true);
            airRoute.set_deleted(false);
            return airRouteRepository.save(airRoute);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AirRoute> findAll() {
        return airRouteRepository.findAll();
    }

    @Override
    public AirRoute update(AirRouteDto airRouteDto) {
        try{
            AirRoute airRoute = airRouteRepository.getById(airRouteDto.getId());
            airRoute.setName(airRouteDto.getName());
            airRoute.setDeparture(airRouteDto.getDeparture());
            airRoute.setDestination(airRouteDto.getDestination());
            return airRouteRepository.save(airRoute);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        AirRoute airRoute = airRouteRepository.getById(id);
        airRoute.set_deleted(true);
        airRoute.set_activated(false);
        airRouteRepository.save(airRoute);
    }

    @Override
    public void enabledById(Long id) {
        AirRoute airRoute = airRouteRepository.getById(id);
        airRoute.set_deleted(false);
        airRoute.set_activated(true);
        airRouteRepository.save(airRoute);
    }

    @Override
    public AirRouteDto getById(Long id) {
        AirRoute airRoute = airRouteRepository.getById(id);
        return mapToDto(airRoute);
    }

    @Override
    public AirRoute findById(Long id) {
        return airRouteRepository.findById(id).get();
    }

    @Override
    public List<AirRoute> findAllByActivated() {
        return airRouteRepository.findAllByActivated();
    }

    @Override
    public Long getAirRouteIdByDepartAndDes(String depart, String des) {
        return airRouteRepository.getAirRouteIdByName(depart, des);
    }

    public AirRouteDto mapToDto(AirRoute airRoute){
        AirRouteDto airRouteDto = new AirRouteDto();
        airRouteDto.setId(airRoute.getId());
        airRouteDto.setName(airRoute.getName());
        airRouteDto.setDeparture(airRoute.getDeparture());
        airRouteDto.setDestination(airRoute.getDestination());
        airRouteDto.set_activated(airRoute.is_activated());
        airRouteDto.set_deleted(airRoute.is_deleted());
        return airRouteDto;
    }

    private List<AirRouteDto> mapListToDto(List<AirRoute> airRoutes){
        List<AirRouteDto> airRouteDtos = new ArrayList<>();
        for (AirRoute airRoute : airRoutes){
            AirRouteDto airRouteDto = mapToDto(airRoute);
            airRouteDtos.add(airRouteDto);
        }

        return airRouteDtos;
    }
}
