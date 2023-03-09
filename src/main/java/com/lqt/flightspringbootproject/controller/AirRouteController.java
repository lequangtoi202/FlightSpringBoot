package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.AirRouteDto;
import com.lqt.flightspringbootproject.model.AirRoute;
import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.service.AirRouteService;
import com.lqt.flightspringbootproject.service.AirportService;
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
public class AirRouteController {

    private final AirRouteService airRouteService;
    private final AirportService airportService;

    public AirRouteController(AirRouteService airRouteService, AirportService airportService) {
        this.airRouteService = airRouteService;
        this.airportService = airportService;
    }

    @GetMapping("/air-routes")
    public String airRoutes(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<AirRoute> airRoutes = airRouteService.findAll();
            model.addAttribute("airRoutes", airRoutes);
            model.addAttribute("size", airRoutes.size());
            model.addAttribute("title", "Air Route");
            return "air-routes";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-air-route")
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
            List<Airport> airports = airportService.findAllByActivated();
            model.addAttribute("airports", airports);
            model.addAttribute("airRouteDto", new AirRouteDto());
            return "add-air-route";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-air-route")
    public String add(@ModelAttribute("airRouteDto") AirRouteDto airRouteDto, RedirectAttributes redirectAttributes) {
        try {
            airRouteService.save(airRouteDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/air-routes";
    }

    @GetMapping("/update-air-route/{id}")
    public String updateAirRouteForm(@PathVariable("id") Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            AirRouteDto airRouteDto = airRouteService.getById(id);
            List<Airport> airports = airportService.findAllByActivated();
            model.addAttribute("airports", airports);
            model.addAttribute("title", "Update Air Route");
            model.addAttribute("airRouteDto", airRouteDto);
        return "update-air-route";
        }
        return "redirect:/login";
    }

    @PostMapping("/update-air-route/{id}")
    public String updateAirRoute(@PathVariable("id") Long id,
                                @ModelAttribute("airRouteDto") AirRouteDto airRouteDto,
                                Model model,
                                RedirectAttributes attributes){
        try{
            airRouteService.update(airRouteDto);
            attributes.addFlashAttribute("success", "Updated air route successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update air route");
        }
        return "redirect:/air-routes";
    }

    @RequestMapping(value = "/delete-air-route/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            AirRoute airRoute = airRouteService.findById(id);
            if (airRoute != null){
                airRouteService.deleteById(airRoute.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/air-routes";
    }

    @RequestMapping(value = "/enable-air-route/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            AirRoute airRoute = airRouteService.findById(id);
            if (airRoute != null){
                airRouteService.enabledById(airRoute.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/air-routes";
    }
}
