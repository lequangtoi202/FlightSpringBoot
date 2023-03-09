package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.TransitDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.service.AirRouteService;
import com.lqt.flightspringbootproject.service.AirportService;
import com.lqt.flightspringbootproject.service.TransitService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class TransitController {
    private final AirportService airportService;
    private final AirRouteService airRouteService;
    private final TransitService transitService;

    public TransitController(AirportService airportService, AirRouteService airRouteService, TransitService transitService) {
        this.airportService = airportService;
        this.airRouteService = airRouteService;
        this.transitService = transitService;
    }

    @GetMapping("/transits")
    public String transits(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Transit> transits = transitService.findAll();
            model.addAttribute("transits", transits);
            model.addAttribute("size", transits.size());
            model.addAttribute("title", "Transit");
            return "transits";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-transit")
    public String add(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Airport> airports = airportService.findAllByActivated();
            List<AirRoute> airRoutes = airRouteService.findAllByActivated();
            model.addAttribute("airports", airports);
            model.addAttribute("airRoutes", airRoutes);
            model.addAttribute("transitDto", new TransitDto());
            return "add-transit";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-transit")
    public String add(@ModelAttribute("transitDto") TransitDto transitDto, RedirectAttributes redirectAttributes) {
        try {
            transitService.save(transitDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/transits";
    }

    @GetMapping("/update-transit/{id}")
    public String updateTransitForm(@PathVariable("id") Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("title", "Update Transit");
            List<Airport> airports = airportService.findAllByActivated();
            List<AirRoute> airRoutes = airRouteService.findAllByActivated();
            TransitDto transitDto = transitService.getById(id);
            model.addAttribute("airports", airports);
            model.addAttribute("airRoutes", airRoutes);
            model.addAttribute("transitDto", transitDto);
            return "update-transit";
        }
        return "redirect:/login";
    }

    @PostMapping("/update-transit/{id}")
    public String updateTransit(@PathVariable("id") Long id,
                               @ModelAttribute("transitDto") TransitDto transitDto,
                               Model model,
                               RedirectAttributes attributes){
        try{
            transitService.update(transitDto);
            attributes.addFlashAttribute("success", "Updated transit successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update transit");
        }
        return "redirect:/transits";
    }

    @RequestMapping(value = "/delete-transit/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Transit transit = transitService.findById(id);
            if (transit != null){
                transitService.deleteById(transit.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/transits";
    }

    @RequestMapping(value = "/enable-transit/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Transit transit = transitService.findById(id);
            if (transit != null){
                transitService.enabledById(transit.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/transits";
    }
}
