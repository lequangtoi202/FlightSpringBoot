package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.AirportDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/airports")
    public String flights(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Airport> airports = airportService.findAll();
            model.addAttribute("airports", airports);
            model.addAttribute("size", airports.size());
            model.addAttribute("title", "Airport");
            return "airports";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-airport")
    public String add(Model model, Principal principal) {
        if (principal == null){
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("airportDto", new AirportDto());
            return "add-airport";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-airport")
    public String add(@ModelAttribute("airportDto") AirportDto airportDto, RedirectAttributes redirectAttributes) {
        try {
            airportService.save(airportDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/airports";
    }

    @GetMapping("/update-airport/{id}")
    public String updateAirportForm(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("title", "Update Airport");
            AirportDto airportDto = airportService.getById(id);
            model.addAttribute("airportDto", airportDto);
            return "update-airport";
        }

        return "redirect:/login";
    }

    @PostMapping("/update-airport/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("airportDto") AirportDto airportDto,
                                Model model,
                                RedirectAttributes attributes){
        try{
            airportService.update(airportDto);
            attributes.addFlashAttribute("success", "Updated airport successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update airport");
        }
        return "redirect:/airports";
    }

    @RequestMapping(value = "/delete-airport/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Airport airport = airportService.findById(id);
            if (airport != null){
                airportService.deleteById(airport.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/airports";
    }

    @RequestMapping(value = "/enable-airport/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Airport airport = airportService.findById(id);
            if (airport != null){
                airportService.enabledById(airport.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/airports";
    }
}
