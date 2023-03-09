package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.model.Flight;
import com.lqt.flightspringbootproject.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;
    private int seat_class;
    private LocalDateTime sold_time;
    private Customer customer;
    private Seat seat;
    private Flight flight;
}
