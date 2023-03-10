package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.SeatDto;
import com.lqt.flightspringbootproject.dto.TransitDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.service.AirplaneService;
import com.lqt.flightspringbootproject.service.SeatService;
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
public class SeatController {

    private final AirplaneService airplaneService;
    private final SeatService seatService;

    public SeatController(AirplaneService airplaneService, SeatService seatService) {
        this.airplaneService = airplaneService;
        this.seatService = seatService;
    }

    @GetMapping("/seats")
    public String transits(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Seat> seats = seatService.findAll();
            model.addAttribute("seats", seats);
            model.addAttribute("size", seats.size());
            model.addAttribute("title", "Seat");
            return "seats";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-seat")
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
            List<Airplane> airplanes = airplaneService.findAllByActivated();
            model.addAttribute("airplanes", airplanes);
            model.addAttribute("seatDto", new SeatDto());
            return "add-seat";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-seat")
    public String add(@ModelAttribute("seatDto") SeatDto seatDto, RedirectAttributes redirectAttributes) {
        try {
            seatService.save(seatDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/seats";
    }

    @GetMapping("/update-seat/{id}")
    public String updateFlightForm(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("title", "Update Seat");
            List<Airplane> airplanes = airplaneService.findAllByActivated();
            SeatDto seatDto = seatService.getById(id);
            model.addAttribute("airplanes", airplanes);
            model.addAttribute("seatDto", seatDto);
            return "update-seat";
        }
        return "redirect:/login";
    }

    @PostMapping("/update-seat/{id}")
    public String updateFlight(@PathVariable("id") Long id,
                               @ModelAttribute("seatDto") SeatDto seatDto,
                               Model model,
                               RedirectAttributes attributes){
        try{
            seatService.update(seatDto);
            attributes.addFlashAttribute("success", "Updated seat successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update seat");
        }
        return "redirect:/seats";
    }

    @RequestMapping(value = "/delete-seat/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Seat seat = seatService.findById(id);
            if (seat != null){
                seatService.deleteById(seat.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/transits";
    }

    @RequestMapping(value = "/enable-seat/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Seat seat = seatService.findById(id);
            if (seat != null){
                seatService.enabledById(seat.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/seats";
    }
}
