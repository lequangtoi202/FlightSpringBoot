package com.lqt.flightspringbootproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flight")
    private List<Ticket> ticket;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flight")
    private List<Schedule> schedule;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_price_id", referencedColumnName = "ticket_price_id")
    private TicketPrice ticketPrice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "air_route_id", referencedColumnName = "air_route_id")
    private AirRoute airRoute;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_id", referencedColumnName = "airplane_id")
    private Airplane airplane;

    private boolean is_deleted;
    private boolean is_activated;
}
