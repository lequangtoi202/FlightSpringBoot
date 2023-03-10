package com.lqt.flightspringbootproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airplane")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airplane_id")
    private Long Id;
    private double capacity;
    private String name;
    private String brand;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airplane")
    private List<Seat> seat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airplane")
    private List<Flight> flight;

    private boolean is_deleted;
    private boolean is_activated;

    public Airplane(double capacity, String name, String brand, Type type) {
        this.capacity = capacity;
        this.name = name;
        this.brand = brand;
        this.type = type;
    }
}
