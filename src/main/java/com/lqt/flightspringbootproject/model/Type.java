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
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long id;

    private String model;
    private String generation;
    private int num_o_seat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    private List<Airplane> airplane;

    private boolean is_deleted;
    private boolean is_activated;

    public Type(String model, String generation, int num_o_seat){
        this.model = model;
        this.generation = generation;
        this.num_o_seat = num_o_seat;
        this.is_activated = true;
        this.is_deleted = false;
    }
}
