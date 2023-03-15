package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.StatisticResponse;

import java.util.List;

public interface StatisticService {
    List<String> statistic(int seatClass, int year, int month);
}
