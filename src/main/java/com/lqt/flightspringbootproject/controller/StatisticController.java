package com.lqt.flightspringbootproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {
    @GetMapping("/statistics")
    public String statistic(){
        return "statistic";
    }
}
