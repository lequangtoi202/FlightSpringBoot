package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChartController {
    @Autowired
    private StatisticService statisticService;
    @GetMapping("/api/chart")
    public List<String> getChartData(@RequestParam("seatClass")int seatClass, @RequestParam("year") int year, @RequestParam("month")int month ) {
        // Load chart data from the database or another source
        List<String> statisticResponses = statisticService.statistic(seatClass, year, month);
        return statisticResponses;
    }


}
