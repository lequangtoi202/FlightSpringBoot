package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPriceDto {
    private Long id;
    private double F_price;
    private double S_price;
    private boolean is_deleted;
    private boolean is_activated;
}
