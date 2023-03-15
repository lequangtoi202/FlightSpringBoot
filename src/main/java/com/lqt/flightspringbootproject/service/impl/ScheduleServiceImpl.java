package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.dto.ScheduleDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Schedule;
import com.lqt.flightspringbootproject.repository.ScheduleRepository;
import com.lqt.flightspringbootproject.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public Schedule save(ScheduleDto scheduleDto) {
        try{
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Schedule schedule = new Schedule();
            schedule.setDate(fm.parse(scheduleDto.getDate()));
            schedule.setTimeStart(LocalTime.parse(scheduleDto.getTimeStart()));
            schedule.setTimeEnd(LocalTime.parse(scheduleDto.getTimeEnd()));
            schedule.setNum_o_F_seat(scheduleDto.getNum_o_F_seat());
            schedule.setNum_o_S_seat(scheduleDto.getNum_o_S_seat());
            schedule.setFlight(scheduleDto.getFlight());
            schedule.set_activated(true);
            schedule.set_deleted(false);
            return scheduleRepository.save(schedule);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule update(ScheduleDto scheduleDto) {
        try{
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Schedule schedule = scheduleRepository.getById(scheduleDto.getId());
            schedule.setDate(fm.parse(scheduleDto.getDate()));
            schedule.setTimeStart(LocalTime.parse(scheduleDto.getTimeStart()));
            schedule.setTimeEnd(LocalTime.parse(scheduleDto.getTimeEnd()));
            schedule.setNum_o_F_seat(scheduleDto.getNum_o_F_seat());
            schedule.setNum_o_S_seat(scheduleDto.getNum_o_S_seat());
            schedule.setFlight(scheduleDto.getFlight());
            return scheduleRepository.save(schedule);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Schedule updateNumOfSeat(int seat_class, int qty, Long flightId) {
        Schedule schedule = scheduleRepository.findByFlight(flightId);
        if (seat_class == 1){
            schedule.setNum_o_F_seat(schedule.getNum_o_F_seat() - qty);
        }else{
            schedule.setNum_o_S_seat(schedule.getNum_o_S_seat() - qty);
        }

        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteById(Long id) {
        Schedule schedule = scheduleRepository.getById(id);
        schedule.set_deleted(true);
        schedule.set_activated(false);
        scheduleRepository.save(schedule);
    }

    @Override
    public void enabledById(Long id) {
        Schedule schedule = scheduleRepository.getById(id);
        schedule.set_deleted(false);
        schedule.set_activated(true);
        scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleDto getById(Long id) {
        Schedule schedule = scheduleRepository.getById(id);
        return mapToDto(schedule);
    }

    @Override
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).get();
    }

    @Override
    public Schedule getByFlightId(Long flightId) {
        return scheduleRepository.findByFlight(flightId);
    }

    @Override
    public List<Schedule> findAllByActivated() {
        return scheduleRepository.findAllByActivated();
    }

    public ScheduleDto mapToDto(Schedule schedule){
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDate(schedule.getDate().toString());
        scheduleDto.setTimeStart(schedule.getTimeStart().toString());
        scheduleDto.setTimeEnd(schedule.getTimeEnd().toString());
        scheduleDto.setNum_o_F_seat(schedule.getNum_o_F_seat());
        scheduleDto.setNum_o_S_seat(schedule.getNum_o_S_seat());
        scheduleDto.setFlight(schedule.getFlight());
        scheduleDto.set_activated(schedule.is_activated());
        scheduleDto.set_deleted(schedule.is_deleted());
        return scheduleDto;
    }

    private List<ScheduleDto> mapListToDto(List<Schedule> schedules){
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule schedule : schedules){
            ScheduleDto scheduleDto = mapToDto(schedule);
            scheduleDtos.add(scheduleDto);
        }

        return scheduleDtos;
    }
}
