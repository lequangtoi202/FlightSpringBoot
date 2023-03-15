package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.UserDto;
import com.lqt.flightspringbootproject.model.User;
import com.lqt.flightspringbootproject.service.impl.UserServiceImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class LoginController {
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserServiceImpl userService;

    public LoginController(BCryptPasswordEncoder passwordEncoder, UserServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(name = "logout", required = false) String logout, HttpServletRequest request, Model model){
        if (logout != null){
            HttpSession session = request.getSession(false);
            SecurityContextHolder.clearContext();


            session = request.getSession(false);
            if(session != null) {
                session.invalidate();
            }
        }
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto userDto,
                                      BindingResult result,
                                      Model model) {
        try {
            if (result.hasErrors()){
                model.addAttribute("userDto", userDto);
                result.toString();
                return "register";
            }

            String username = userDto.getUsername();
            User user = userService.findByUsername(username);
            if (user != null){
                model.addAttribute("userDto", userDto);
                model.addAttribute("emailErrors", "Email or username has been registered");
                return "register";
            }

            if (userDto.getPassword().equals(userDto.getRepeatPassword())){
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userService.save(userDto);
                model.addAttribute("userDto", userDto);
                model.addAttribute("success", "Register successfully!");
            }
            else{
                model.addAttribute("userDto", userDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Please check again.");
                return "register";
            }
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errors", "Can not register because error server");
        }

        return "register";
    }

    @GetMapping("/admin")
    public String adminHome(Model model){
        return "index";
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session, Principal principal, HttpServletRequest request){
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            HttpSession session1 = request.getSession();
            String userAdmin = (String) session.getAttribute("userAdmin");

            return "redirect:/admin";
        }
        session.setAttribute("username", principal.getName());
        return "/customer/home";
    }
}
