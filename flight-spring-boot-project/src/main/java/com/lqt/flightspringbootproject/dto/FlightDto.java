package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {
    private Long id;
    private TicketPrice ticketPrice;
    private AirRoute airRoute;
    private Airplane airplane;
    private boolean is_deleted;
    private boolean is_activated;
}
