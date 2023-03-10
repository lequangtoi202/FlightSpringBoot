package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Type;
import com.lqt.flightspringbootproject.service.AirplaneService;
import com.lqt.flightspringbootproject.service.TypeService;
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
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final TypeService typeService;

    public AirplaneController(AirplaneService airplaneService, TypeService typeService) {
        this.airplaneService = airplaneService;
        this.typeService = typeService;
    }

    @GetMapping("/airplanes")
    public String airplanes(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Airplane> airplanes = airplaneService.findAll();
            model.addAttribute("airplanes", airplanes);
            model.addAttribute("size", airplanes.size());
            model.addAttribute("title", "Airplane");
            return "airplanes";
        }
        return "redirect:/login";

    }

    @GetMapping("/add-airplane")
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
            List<Type> types = typeService.findAllByActivated();
            model.addAttribute("types", types);
            model.addAttribute("airplaneDto", new AirplaneDto());
            return "add-airplane";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-airplane")
    public String add(@ModelAttribute("airplaneDto") AirplaneDto airplaneDto, RedirectAttributes redirectAttributes) {
        try {
            airplaneService.save(airplaneDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/airplanes";
    }

    @GetMapping("/update-airplane/{id}")
    public String updateAirplaneForm(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("title", "Update Product");
            List<Type> types = typeService.findAllByActivated();
            AirplaneDto airplaneDto = airplaneService.getById(id);
            model.addAttribute("types", types);
            model.addAttribute("airplaneDto", airplaneDto);
            return "update-airplane";
        }
        return "redirect:/login";
    }

    @PostMapping("/update-airplane/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("airplaneDto") AirplaneDto airplaneDto,
                                Model model,
                                RedirectAttributes attributes){
        try{
            airplaneService.update(airplaneDto);
            attributes.addFlashAttribute("success", "Updated product successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update product");
        }
        return "redirect:/airplanes";
    }

    @RequestMapping(value = "/delete-airplane/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Airplane airplane = airplaneService.findById(id);
            if (airplane != null){
                airplaneService.deleteById(airplane.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/airplanes";
    }

    @RequestMapping(value = "/enable-airplane/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            Airplane airplane = airplaneService.findById(id);
            if (airplane != null){
                airplaneService.enabledById(airplane.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/airplanes";
    }

}
