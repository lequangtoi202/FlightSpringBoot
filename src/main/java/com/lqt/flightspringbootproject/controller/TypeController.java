package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.TypeDto;
import com.lqt.flightspringbootproject.model.Type;
import com.lqt.flightspringbootproject.service.TypeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types")
    public String types(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            List<Type> types = typeService.findAll();
            model.addAttribute("types", types);
            model.addAttribute("size", types.size());
            model.addAttribute("title", "Type");
            model.addAttribute("typeNew", new Type());
            return "types";
        }
        return "redirect:/login";
    }

    @PostMapping("/add-type")
    public String add(@ModelAttribute("typeNew")TypeDto typeDto, RedirectAttributes redirectAttributes) {
        try{
            typeService.save(typeDto);
            redirectAttributes.addFlashAttribute("success", "Added successfully!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add because duplicate model");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/types";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Type findById(Long id){
        return typeService.findById(id);
    }

    @GetMapping("/update-type")
    public String update(Type type, RedirectAttributes redirectAttributes){
        try {
            typeService.update(type);
            redirectAttributes.addFlashAttribute("success", "Updated successfully!");
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update because duplicate model");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/types";
    }

    @RequestMapping(value = "/delete-type", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes){
        try {
            Type type = typeService.findById(id);
            if (type != null){
                typeService.deleteById(type.getId());
            }
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete");
        }
        return "redirect:/types";
    }

    @RequestMapping(value = "/enable-type", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes){
        try {
            Type type = typeService.findById(id);
            if (type != null){
                typeService.enabledById(type.getId());
            }
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable");
        }
        return "redirect:/types";
    }
}
