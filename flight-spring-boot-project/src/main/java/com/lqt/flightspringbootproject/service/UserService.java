package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.UserDto;
import com.lqt.flightspringbootproject.model.User;


public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);

    void processOAuthPostLogin(String username);
}
