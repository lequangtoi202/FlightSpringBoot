package com.lqt.flightspringbootproject.service;


import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.ScheduleDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Schedule;
import com.lqt.flightspringbootproject.model.Type;

import java.util.List;

public interface ScheduleService {
    Schedule save(ScheduleDto scheduleDto);

    List<Schedule> findAll();

    Schedule update(ScheduleDto scheduleDto);

    Schedule updateNumOfSeat(int seat_class, int qty, Long flightId);

    void deleteById(Long id);

    void enabledById(Long id);

    ScheduleDto getById(Long id);

    Schedule findById(Long id);

    Schedule getByFlightId(Long flightId);

    List<Schedule> findAllByActivated();
}
