package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.TicketPrice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
public class FlightResponse {
    private Long id;
    private Airplane airplane;
    private AirRoute airRoute;
    private TicketPrice ticketPrice;
    private boolean is_activated;
    private boolean is_deleted;
    private Date date;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public FlightResponse(Long id, Airplane airplane, AirRoute airRoute, TicketPrice ticketPrice, boolean is_activated, boolean is_deleted, Date date, LocalTime timeStart, LocalTime timeEnd) {
        this.id = id;
        this.airplane = airplane;
        this.airRoute = airRoute;
        this.ticketPrice = ticketPrice;
        this.is_activated = is_activated;
        this.is_deleted = is_deleted;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
}
