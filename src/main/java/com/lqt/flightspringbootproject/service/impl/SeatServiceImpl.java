package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.SeatDto;
import com.lqt.flightspringbootproject.model.Seat;
import com.lqt.flightspringbootproject.repository.SeatRepository;
import com.lqt.flightspringbootproject.service.FlightService;
import com.lqt.flightspringbootproject.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final FlightService flightService;

    public SeatServiceImpl(SeatRepository seatRepository, FlightService flightService) {
        this.seatRepository = seatRepository;
        this.flightService = flightService;
    }

    @Override
    public Seat save(SeatDto seatDto) {
        try{
            Seat seat = new Seat();
            seat.setAirplane(seatDto.getAirplane());
            seat.setS_class(seatDto.getS_class());
            seat.setSeatName(seatDto.getSeatName());
            seat.set_activated(true);
            seat.set_deleted(false);
            return seatRepository.save(seat);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat update(SeatDto seatDto) {
        try{
            Seat seat = seatRepository.getById(seatDto.getId());
            seat.setAirplane(seatDto.getAirplane());
            seat.setS_class(seatDto.getS_class());
            seat.setSeatName(seatDto.getSeatName());
            return seatRepository.save(seat);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Seat seat = seatRepository.getById(id);
        seat.set_deleted(true);
        seat.set_activated(false);
        seatRepository.save(seat);
    }

    @Override
    public void enabledById(Long id) {
        Seat seat = seatRepository.getById(id);
        seat.set_deleted(false);
        seat.set_activated(true);
        seatRepository.save(seat);
    }

    @Override
    public SeatDto getById(Long id) {
        Seat seat = seatRepository.getById(id);
        return mapToDto(seat);
    }

    @Override
    public Seat findById(Long id) {
        return seatRepository.findById(id).get();
    }

    @Override
    public List<Seat> findAllByActivated() {
        return seatRepository.findAllByActivated();
    }

    @Override
    public List<Seat> getSeatBySeatClassAndFlight(int seatClass, Long flightId) {
        Long airplaneId = flightService.getAirplaneIdByFlightId(flightId);
        return seatRepository.getAllBySeatClassAndAirplane(seatClass, airplaneId);
    }

    @Override
    public Seat getSeatBySeatClassAndFlightIdAndSeatTemp(int seatClass, Long idTemp, Long flightId) {
        Long airplaneId = flightService.getAirplaneIdByFlightId(flightId);
        Long seatId = seatRepository.getSeatBySeatClass(seatClass, idTemp, airplaneId);
        return seatRepository.getById(seatId);
    }

    public SeatDto mapToDto(Seat seat){
        SeatDto seatDto = new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setAirplane(seat.getAirplane());
        seatDto.setS_class(seat.getS_class());
        seatDto.setSeatName(seat.getSeatName());
        seatDto.set_activated(seat.is_activated());
        seatDto.set_deleted(seat.is_deleted());
        return seatDto;
    }

    private List<SeatDto> mapListToDto(List<Seat> seats){
        List<SeatDto> seatDtos = new ArrayList<>();
        for (Seat seat : seats){
            SeatDto seatDto = mapToDto(seat);
            seatDtos.add(seatDto);
        }

        return seatDtos;
    }
}
