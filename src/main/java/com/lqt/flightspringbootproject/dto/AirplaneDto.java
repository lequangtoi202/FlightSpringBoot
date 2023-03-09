package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Type;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDto {
    private Long id;
    private String name;
    private String brand;
    private double capacity;
    private Type type;
    private boolean is_deleted;
    private boolean is_activated;
}
