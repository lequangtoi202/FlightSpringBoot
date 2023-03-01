package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private Date dob;
    private String address;
    private String position;
    private User user;
    private boolean is_deleted;
    private boolean is_activated;
}
