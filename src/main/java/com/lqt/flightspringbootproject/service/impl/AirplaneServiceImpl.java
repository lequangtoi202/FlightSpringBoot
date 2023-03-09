package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.repository.AirplaneRepository;
import com.lqt.flightspringbootproject.service.AirplaneService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    public Airplane save(AirplaneDto airplaneDto) {
        try{
            Airplane airplane = new Airplane(airplaneDto.getCapacity(),
                    airplaneDto.getName(),
                    airplaneDto.getBrand(),
                    airplaneDto.getType());
            airplane.set_activated(true);
            airplane.set_deleted(false);
            return airplaneRepository.save(airplane);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Airplane> findAll() {
            return airplaneRepository.findAll();
    }

    @Override
    public Airplane update(AirplaneDto airplaneDto) {
        try{
            Airplane airplane = airplaneRepository.getById(airplaneDto.getId());
            airplane.setName(airplaneDto.getName());
            airplane.setBrand(airplaneDto.getBrand());
            airplane.setCapacity(airplaneDto.getCapacity());
            airplane.setType(airplaneDto.getType());
            return airplaneRepository.save(airplane);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Airplane airplane = airplaneRepository.getById(id);
        airplane.set_deleted(true);
        airplane.set_activated(false);
        airplaneRepository.save(airplane);
    }

    @Override
    public void enabledById(Long id) {
        Airplane airplane = airplaneRepository.getById(id);
        airplane.set_deleted(false);
        airplane.set_activated(true);
        airplaneRepository.save(airplane);
    }

    @Override
    public AirplaneDto getById(Long id) {
        Airplane airplane = airplaneRepository.getById(id);
        return mapToDto(airplane);
    }

    @Override
    public Airplane findById(Long id) {
        return airplaneRepository.findById(id).get();
    }

    @Override
    public List<Airplane> findAllByActivated() {
        return airplaneRepository.findAllByActivated();
    }

    public AirplaneDto mapToDto(Airplane airplane){
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.setId(airplane.getId());
        airplaneDto.setName(airplane.getName());
        airplaneDto.setBrand(airplane.getBrand());
        airplaneDto.setCapacity(airplane.getCapacity());
        airplaneDto.setType(airplane.getType());
        airplaneDto.set_activated(airplane.is_activated());
        airplaneDto.set_deleted(airplane.is_deleted());
        return airplaneDto;
    }

    private List<AirplaneDto> mapListToDto(List<Airplane> airplanes){
        List<AirplaneDto> airplaneDtoList = new ArrayList<>();
        for (Airplane airplane : airplanes){
            AirplaneDto airplaneDto = mapToDto(airplane);

            airplaneDtoList.add(airplaneDto);
        }

        return airplaneDtoList;
    }

}
