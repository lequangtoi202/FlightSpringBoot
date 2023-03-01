package com.lqt.flightspringbootproject.service;


import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.ScheduleDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule save(ScheduleDto scheduleDto);

    List<Schedule> findAll();

    Schedule update(ScheduleDto scheduleDto);

    void deleteById(Long id);

    void enabledById(Long id);

    ScheduleDto getById(Long id);

    Schedule findById(Long id);
    List<Schedule> findAllByActivated();
}
