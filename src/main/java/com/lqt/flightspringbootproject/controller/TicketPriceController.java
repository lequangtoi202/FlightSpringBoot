package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.AirplaneDto;
import com.lqt.flightspringbootproject.dto.TicketPriceDto;
import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.TicketPrice;
import com.lqt.flightspringbootproject.model.Type;
import com.lqt.flightspringbootproject.service.TicketPriceService;
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
public class TicketPriceController {
    private final TicketPriceService ticketPriceService;

    public TicketPriceController(TicketPriceService ticketPriceService) {
        this.ticketPriceService = ticketPriceService;
    }

    @GetMapping("/ticket-price")
    public String ticketPrices(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<TicketPrice> ticketPrices = ticketPriceService.findAll();
            model.addAttribute("ticketPrices", ticketPrices);
            model.addAttribute("size", ticketPrices.size());
            model.addAttribute("title", "TicketPrice");
            return "ticket-prices";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-ticket-price")
    public String addTicketPriceForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("ticketPriceDto", new TicketPriceDto());
            return "add-ticket-price";
        }
        return "redirect:/login";
    }

    @PostMapping("/save-ticket-price")
    public String add(@ModelAttribute("ticketPriceDto") TicketPriceDto ticketPriceDto, RedirectAttributes redirectAttributes) {
        try {
            ticketPriceService.save(ticketPriceDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/ticket-price";
    }

    @GetMapping("/update-ticket-price/{id}")
    public String updateTicketPriceForm(@PathVariable("id") Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("title", "Update TicketPrice");
            TicketPriceDto ticketPriceDto = ticketPriceService.getById(id);
            model.addAttribute("ticketPriceDto", ticketPriceDto);
            return "update-ticket-price";
        }
        return "redirect:/login";
    }

    @PostMapping("/update-ticket-price/{id}")
    public String updateTicketPrice(@PathVariable("id") Long id,
                                @ModelAttribute("ticketPriceDto") TicketPriceDto ticketPriceDto,
                                Model model,
                                RedirectAttributes attributes){
        try{
            ticketPriceService.update(ticketPriceDto);
            attributes.addFlashAttribute("success", "Updated product successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update product");
        }
        return "redirect:/ticket-price";
    }

    @RequestMapping(value = "/delete-ticket-price/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            TicketPrice ticketPrice = ticketPriceService.findById(id);
            if (ticketPrice != null){
                ticketPriceService.deleteById(ticketPrice.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/ticket-price";
    }

    @RequestMapping(value = "/enable-ticket-price/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            TicketPrice ticketPrice = ticketPriceService.findById(id);
            if (ticketPrice != null){
                ticketPriceService.enabledById(ticketPrice.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/ticket-price";//
    }
}
