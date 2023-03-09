package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.*;
import lombok.*;

@Getter
@Setter
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
