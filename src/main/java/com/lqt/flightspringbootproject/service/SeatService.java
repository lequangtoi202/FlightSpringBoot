package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.SeatDto;
import com.lqt.flightspringbootproject.model.Seat;

import java.util.List;


public interface SeatService {
    Seat save(SeatDto SeatDto);

    List<Seat> findAll();

    Seat update(SeatDto SeatDto);

    void deleteById(Long id);

    void enabledById(Long id);

    SeatDto getById(Long id);

    Seat findById(Long id);

    List<Seat> findAllByActivated();

    List<Seat> getSeatBySeatClassAndFlight(int seatClass, Long flightId);

    Seat getSeatBySeatClassAndFlightIdAndSeatTemp(int seatClass, Long idTemp, Long flightId);

}
