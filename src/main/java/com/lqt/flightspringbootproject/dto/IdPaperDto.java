package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdPaperDto {
    private Long id;
    private String code;
    private String paper_type;
    private Customer customer;
}
