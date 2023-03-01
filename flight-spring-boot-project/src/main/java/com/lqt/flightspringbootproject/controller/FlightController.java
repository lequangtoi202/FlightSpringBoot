package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.FlightDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.service.AirRouteService;
import com.lqt.flightspringbootproject.service.AirplaneService;
import com.lqt.flightspringbootproject.service.FlightService;
import com.lqt.flightspringbootproject.service.TicketPriceService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class FlightController {

    private final FlightService flightService;
    private final AirplaneService airplaneService;
    private final AirRouteService airRouteService;
    private final TicketPriceService ticketPriceService;

    public FlightController(FlightService flightService, AirplaneService airplaneService, AirRouteService airRouteService, TicketPriceService ticketPriceService) {
        this.flightService = flightService;
        this.airplaneService = airplaneService;
        this.airRouteService = airRouteService;
        this.ticketPriceService = ticketPriceService;
    }

    @GetMapping("/flights")
    public String flights(Model model){
        List<Flight> flights = flightService.findAll();
        model.addAttribute("flights", flights);
        model.addAttribute("size", flights.size());
        model.addAttribute("title", "Flight");
        return "flights";
    }

    @GetMapping("/add-flight")
    public String add(Model model, Principal principal) {
        if (principal == null){
            return "redirect:/login";
        }
        List<Airplane> airplanes = airplaneService.findAllByActivated();
        List<AirRoute> airRoutes = airRouteService.findAllByActivated();
        List<TicketPrice> ticketPrices = ticketPriceService.findAllByActivated();
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("airRoutes", airRoutes);
        model.addAttribute("ticketPrices", ticketPrices);
        model.addAttribute("flightDto", new FlightDto());
        return "add-flight";
    }

    @PostMapping("/save-flight")
    public String add(@ModelAttribute("flightDto") FlightDto flightDto, RedirectAttributes redirectAttributes) {
        try {
            flightService.save(flightDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/flights";
    }

    @GetMapping("/update-flight/{id}")
    public String updateAirplaneForm(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Update Flight");
        List<Airplane> airplanes = airplaneService.findAllByActivated();
        List<AirRoute> airRoutes = airRouteService.findAllByActivated();
        List<TicketPrice> ticketPrices = ticketPriceService.findAllByActivated();
        FlightDto flightDto = flightService.getById(id);
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("airRoutes", airRoutes);
        model.addAttribute("ticketPrices", ticketPrices);
        model.addAttribute("flightDto", flightDto);
        return "update-flight";
    }

    @PostMapping("/update-flight/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("flightDto") FlightDto flightDto,
                                Model model,
                                RedirectAttributes attributes){
        try{
            flightService.update(flightDto);
            attributes.addFlashAttribute("success", "Updated product successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update product");
        }
        return "redirect:/airplanes";
    }

    @RequestMapping(value = "/delete-flight/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Flight flight = flightService.findById(id);
            if (flight != null){
                flightService.deleteById(flight.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/flights";
    }

    @RequestMapping(value = "/enable-flight/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Flight flight = flightService.findById(id);
            if (flight != null){
                flightService.enabledById(flight.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/flights";
    }
}
