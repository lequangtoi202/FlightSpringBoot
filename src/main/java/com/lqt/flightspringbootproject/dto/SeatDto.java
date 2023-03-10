package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Airplane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private Long id;
    private int S_class;
    private String seatName;
    private Airplane airplane;
    private boolean is_deleted;
    private boolean is_activated;
}
