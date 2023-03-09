package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.UserDto;
import com.lqt.flightspringbootproject.model.User;
import com.lqt.flightspringbootproject.repository.UserRepository;
import com.lqt.flightspringbootproject.repository.RoleRepository;
import com.lqt.flightspringbootproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        return userRepository.save(user);
    }

    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.getUserByUsername(username);

        if (existUser == null) {
            User newAcc = new User();
            newAcc.setUsername(username);

            userRepository.save(newAcc);
        }

    }
}
