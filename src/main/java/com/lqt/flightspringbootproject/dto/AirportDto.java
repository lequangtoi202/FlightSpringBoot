package com.lqt.flightspringbootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDto {
    private Long id;
    private String name;
    private String location;
    private boolean is_deleted;
    private boolean is_activated;
}
