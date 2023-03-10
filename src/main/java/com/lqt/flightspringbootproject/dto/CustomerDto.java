package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private Date dob;
    private String address;
    private User user;
}
