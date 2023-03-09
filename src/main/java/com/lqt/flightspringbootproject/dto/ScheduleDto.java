package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private String date;
    private String timeStart;
    private String timeEnd;
    private int num_o_F_seat;
    private int num_o_S_seat;
    private Flight flight;
    private boolean is_deleted;
    private boolean is_activated;
}
