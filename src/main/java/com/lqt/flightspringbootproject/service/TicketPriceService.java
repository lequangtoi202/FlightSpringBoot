package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.TicketPriceDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.TicketPrice;

import java.util.List;

public interface TicketPriceService {

    TicketPrice save(TicketPriceDto ticketPriceDto);

    List<TicketPrice> findAll();

    TicketPrice update(TicketPriceDto ticketPriceDto);

    void deleteById(Long id);

    void enabledById(Long id);

    TicketPriceDto getById(Long id);

    TicketPrice findById(Long id);
    List<TicketPrice> findAllByActivated();
}
