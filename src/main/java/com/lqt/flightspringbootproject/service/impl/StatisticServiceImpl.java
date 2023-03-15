package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.StatisticResponse;
import com.lqt.flightspringbootproject.repository.FlightRepository;
import com.lqt.flightspringbootproject.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl  implements StatisticService {
    @Autowired
    private FlightRepository flightRepository;

    @Override
    public List<String> statistic(int seatClass, int year, int month) {
        return flightRepository.statistic(seatClass, year, month);
    }
}
