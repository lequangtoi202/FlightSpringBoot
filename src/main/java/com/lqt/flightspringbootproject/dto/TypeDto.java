package com.lqt.flightspringbootproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {
    private String model;
    private String generation;
    private int num_o_seat;
}
