package com.lqt.flightspringbootproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="ticket_price")
public class TicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_price_id")
    private Long id;

    private double F_price;
    private double S_price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketPrice")
    private List<Flight> flight;

    private boolean is_deleted;
    private boolean is_activated;
}
