package com.lqt.flightspringbootproject.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="transit")
public class Transit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transit_id")
    private Long id;
    private double stop_minutes;
    private String note;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "airport_id", referencedColumnName = "airport_id")
    private Airport airport;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "air_route_id", referencedColumnName = "air_route_id")
    private AirRoute airRoute;

    private boolean is_deleted;
    private boolean is_activated;
}
