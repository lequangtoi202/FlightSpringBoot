package com.lqt.flightspringbootproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "air_route")
public class AirRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "air_route_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_id", referencedColumnName = "airport_id")
    private Airport departure;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id", referencedColumnName = "airport_id")
    private Airport destination;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airRoute")
    private List<Transit> transit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airRoute")
    private List<Flight> flight;

    private boolean is_deleted;
    private boolean is_activated;

}
