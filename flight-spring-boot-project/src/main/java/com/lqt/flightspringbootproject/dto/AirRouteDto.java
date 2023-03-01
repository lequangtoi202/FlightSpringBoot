package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Airport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirRouteDto {
    private Long id;
    private String name;
    private Airport departure;
    private Airport destination;
    private boolean is_deleted;
    private boolean is_activated;
}
