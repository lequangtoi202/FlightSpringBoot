package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.TicketPriceDto;
import com.lqt.flightspringbootproject.model.TicketPrice;
import com.lqt.flightspringbootproject.repository.TicketPriceRepository;
import com.lqt.flightspringbootproject.service.TicketPriceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPriceServiceImpl implements TicketPriceService {

    private final TicketPriceRepository ticketPriceRepository;

    public TicketPriceServiceImpl(TicketPriceRepository ticketPriceRepository) {
        this.ticketPriceRepository = ticketPriceRepository;
    }

    @Override
    public TicketPrice save(TicketPriceDto ticketPriceDto) {
        try{
            TicketPrice ticketPrice = new TicketPrice();
            ticketPrice.setS_price(ticketPriceDto.getS_price());
            ticketPrice.setF_price(ticketPriceDto.getF_price());
            ticketPrice.set_activated(true);
            ticketPrice.set_deleted(false);
            return ticketPriceRepository.save(ticketPrice);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TicketPrice> findAll() {
        return ticketPriceRepository.findAll();
    }

    @Override
    public TicketPrice update(TicketPriceDto ticketPriceDto) {
        try{
            TicketPrice ticketPrice = ticketPriceRepository.getById(ticketPriceDto.getId());
            ticketPrice.setF_price(ticketPriceDto.getF_price());
            ticketPrice.setS_price(ticketPriceDto.getS_price());
            return ticketPriceRepository.save(ticketPrice);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        TicketPrice ticketPrice = ticketPriceRepository.getById(id);
        ticketPrice.set_deleted(true);
        ticketPrice.set_activated(false);
        ticketPriceRepository.save(ticketPrice);
    }

    @Override
    public void enabledById(Long id) {
        TicketPrice ticketPrice = ticketPriceRepository.getById(id);
        ticketPrice.set_deleted(false);
        ticketPrice.set_activated(true);
        ticketPriceRepository.save(ticketPrice);
    }

    @Override
    public TicketPriceDto getById(Long id) {
        TicketPrice ticketPrice = ticketPriceRepository.getById(id);
        return mapToDto(ticketPrice);
    }

    @Override
    public TicketPrice findById(Long id) {
        return ticketPriceRepository.findById(id).get();
    }

    @Override
    public List<TicketPrice> findAllByActivated() {
        return ticketPriceRepository.findAllByActivated();
    }

    public TicketPriceDto mapToDto(TicketPrice ticketPrice){
        TicketPriceDto ticketPriceDto = new TicketPriceDto();
        ticketPriceDto.setId(ticketPrice.getId());
        ticketPriceDto.setF_price(ticketPrice.getF_price());
        ticketPriceDto.setS_price(ticketPrice.getS_price());
        ticketPriceDto.set_activated(ticketPrice.is_activated());
        ticketPriceDto.set_deleted(ticketPrice.is_deleted());
        return ticketPriceDto;
    }
}
