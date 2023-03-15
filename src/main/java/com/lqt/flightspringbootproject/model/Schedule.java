package com.lqt.flightspringbootproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    private Date date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private int num_o_F_seat;
    private int num_o_S_seat;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id")
    private Flight flight;

    private boolean is_deleted;
    private boolean is_activated;
}
