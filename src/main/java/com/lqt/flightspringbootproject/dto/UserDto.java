package com.lqt.flightspringbootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    @Size(min = 1, max = 20, message = "Invalid first name!(1-20 characters)")
    private String firstName;

    @Size(min = 2, max = 20, message = "Invalid last name!(2-20 characters)")
    private String lastName;

    private String username;

    @Size(min = 3, max = 25, message = "Invalid password!(5-25 characters)")
    private String password;

    private String repeatPassword;
}
