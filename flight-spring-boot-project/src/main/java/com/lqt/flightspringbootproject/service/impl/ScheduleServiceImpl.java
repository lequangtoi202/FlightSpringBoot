package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.dto.ScheduleDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Schedule;
import com.lqt.flightspringbootproject.repository.ScheduleRepository;
import com.lqt.flightspringbootproject.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            Schedule schedule = new Schedule();
            schedule.setTime(scheduleDto.getTime());
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
            Schedule schedule = scheduleRepository.getById(scheduleDto.getId());
            schedule.setTime(scheduleDto.getTime());
            schedule.setNum_o_F_seat(scheduleDto.getNum_o_F_seat());
            schedule.setNum_o_S_seat(scheduleDto.getNum_o_S_seat());
            schedule.setFlight(scheduleDto.getFlight());
            return scheduleRepository.save(schedule);
        }catch (Exception e){
        }
        return null;
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
    public List<Schedule> findAllByActivated() {
        return scheduleRepository.findAllByActivated();
    }

    public ScheduleDto mapToDto(Schedule schedule){
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setTime(schedule.getTime());
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
