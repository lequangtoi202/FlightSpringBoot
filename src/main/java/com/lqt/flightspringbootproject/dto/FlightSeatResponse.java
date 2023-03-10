package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.TicketPrice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightSeatResponse {
    private Long id;
    private TicketPrice ticketPrice;
    private int num_of_f_seat;
    private int num_of_s_seat;

    public FlightSeatResponse(Long id, TicketPrice ticketPrice, int num_of_f_seat, int num_of_s_seat){

        this.id = id;
        this.ticketPrice = ticketPrice;
        this.num_of_f_seat = num_of_f_seat;
        this.num_of_s_seat = num_of_s_seat;
    }
}
