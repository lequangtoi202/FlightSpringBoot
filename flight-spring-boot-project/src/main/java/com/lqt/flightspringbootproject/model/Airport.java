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
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long id;

    private String name;
    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departure")
    private List<AirRoute> departure;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airport")
    private List<Transit> transit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destination")
    private List<AirRoute> destination;

    private boolean is_deleted;
    private boolean is_activated;

}
