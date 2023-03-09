package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Airport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransitDto {
    private Long id;
    private double stop_minutes;
    private String note;
    private Airport airport;
    private AirRoute airRoute;
    private boolean is_deleted;
    private boolean is_activated;
}
