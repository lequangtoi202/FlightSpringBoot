package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.ScheduleDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.service.FlightService;
import com.lqt.flightspringbootproject.service.ScheduleService;
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
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final FlightService flightService;

    public ScheduleController(ScheduleService scheduleService, FlightService flightService) {
        this.scheduleService = scheduleService;
        this.flightService = flightService;
    }

    @GetMapping("/schedules")
    public String flights(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Schedule> schedules = scheduleService.findAll();
            model.addAttribute("schedules", schedules);
            model.addAttribute("size", schedules.size());
            model.addAttribute("title", "Schedule");
            return "schedules";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-schedule")
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
            List<Flight> flights = flightService.findAllByActivated();
            model.addAttribute("flights", flights);
            model.addAttribute("scheduleDto", new ScheduleDto());
            return "add-schedule";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-schedule")
    public String add(@ModelAttribute("scheduleDto") ScheduleDto scheduleDto, RedirectAttributes redirectAttributes) {
        try {
            scheduleService.save(scheduleDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/schedules";
    }

    @GetMapping("/update-schedule/{id}")
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
            model.addAttribute("title", "Update Schedule");
            List<Flight> flights = flightService.findAllByActivated();
            ScheduleDto scheduleDto = scheduleService.getById(id);
            model.addAttribute("flights", flights);
            model.addAttribute("scheduleDto", scheduleDto);
            return "update-schedule";
        }
        return "redirect:/login";
    }

    @PostMapping("/update-schedule/{id}")
    public String updateFlight(@PathVariable("id") Long id,
                               @ModelAttribute("scheduleDto") ScheduleDto scheduleDto,
                               Model model,
                               RedirectAttributes attributes){
        try{
            scheduleService.update(scheduleDto);
            attributes.addFlashAttribute("success", "Updated product successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update product");
        }
        return "redirect:/schedules";
    }

    @RequestMapping(value = "/delete-schedule/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Schedule schedule = scheduleService.findById(id);
            if (schedule != null){
                scheduleService.deleteById(schedule.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/schedules";
    }

    @RequestMapping(value = "/enable-schedule/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Schedule schedule = scheduleService.findById(id);
            if (schedule != null){
                scheduleService.enabledById(schedule.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/schedules";
    }
}
