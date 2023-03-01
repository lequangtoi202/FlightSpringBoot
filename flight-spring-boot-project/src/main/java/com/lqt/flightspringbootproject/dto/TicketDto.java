package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.model.Employee;
import com.lqt.flightspringbootproject.model.Flight;
import com.lqt.flightspringbootproject.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;
    private int seat_class;
    private Date sold_time;
    private Employee employee;
    private Customer customer;
    private Seat seat;
    private Flight flight;
    private boolean is_deleted;
    private boolean is_activated;
}
