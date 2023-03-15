package com.lqt.flightspringbootproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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
