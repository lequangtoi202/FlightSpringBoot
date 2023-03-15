package com.lqt.flightspringbootproject.controller;

import com.lqt.flightspringbootproject.dto.CustomerDto;
import com.lqt.flightspringbootproject.dto.FlightResponse;
import com.lqt.flightspringbootproject.dto.IdPaperDto;
import com.lqt.flightspringbootproject.model.*;
import com.lqt.flightspringbootproject.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class CustomerController {
    private final FlightService flightService;
    private final CustomerService customerService;
    private final UserService userService;
    private final TicketService ticketService;
    private final IdPaperService idPaperService;
    private final ScheduleService scheduleService;
    private final SeatService seatService;

    public CustomerController(FlightService flightService, CustomerService customerService, UserService userService, TicketService ticketService, IdPaperService idPaperService, ScheduleService scheduleService, SeatService seatService) {
        this.flightService = flightService;
        this.customerService = customerService;
        this.userService = userService;
        this.ticketService = ticketService;
        this.idPaperService = idPaperService;
        this.scheduleService = scheduleService;
        this.seatService = seatService;
    }

    @GetMapping("/search-flights")
    public String searchFlights(Model model,
                                Principal principal,
                                @RequestParam(name = "departure") String departure,
                                @RequestParam(name = "destination") String destination,
                                @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<FlightResponse> flights = flightService.getFlights(departure, destination, date);
        model.addAttribute("flights", flights);
        model.addAttribute("size", flights.size());
        return "/customer/cus-flights";
    }

    @GetMapping("/flight-detail/{id}")
    public String flightDetail(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        FlightResponse flightResponse = flightService.getFlightByIdAndIsActivated(id);
        SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
        String date = fm.format(flightResponse.getDate());
        int f_seat = flightService.getRemainingSeat(flightResponse.getId(), 1);
        int s_seat = flightService.getRemainingSeat(flightResponse.getId(), 2);
        model.addAttribute("date", date);
        model.addAttribute("f_seat", f_seat);
        model.addAttribute("s_seat", s_seat);
        model.addAttribute("flight", flightResponse);
        return "/customer/flight-details";
    }

    @GetMapping("/flight-detail/{id}/fill-info")
    public String fillInfo(@PathVariable("id") Long id,
                           @RequestParam("seat_class") int seat_class,
                           @RequestParam("adult") int adult,
                           @RequestParam("child") int child,
                           Model model,
                           Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }

        double price = 0.0;
        double totalCost = 0.0;
        FlightResponse flightResponse = flightService.getFlightByIdAndIsActivated(id);
        if (seat_class == 1) {
            price = flightResponse.getTicketPrice().getF_price();
        } else if (seat_class == 2) {
            price = flightResponse.getTicketPrice().getS_price();
        }
        totalCost = (adult + child) * price;
        session.setAttribute("total", totalCost);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("price", price);
        model.addAttribute("adult", adult);
        model.addAttribute("child", child);
        model.addAttribute("seat_class", seat_class);
        model.addAttribute("flight", flightResponse);
        return "/customer/fill-info";
    }

    @GetMapping("/flight-detail/{id}/ticket/{seat_class}")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public String ticket(@PathVariable("id") Long id, @PathVariable("seat_class") int seat_class,
                         @RequestParam(value = "AFname", required = false) String[] AFname,
                         @RequestParam(value = "ALname", required = false) String[] ALname,
                         @RequestParam(value = "ADOB", required = false) String[] ADOB,
                         @RequestParam(value = "APhone", required = false) String[] APhone,
                         @RequestParam(value = "paperType", required = false) String[] paperType,
                         @RequestParam(value = "APaper", required = false) String[] APaper,
                         @RequestParam(value = "CFname", required = false) String[] CFname,
                         @RequestParam(value = "CLname", required = false) String[] CLname,
                         @RequestParam(value = "CDOB", required = false) String[] CDOB,
                         @RequestParam(value = "CPhone", required = false) String[] CPhone,
                         @RequestParam(value = "CPP", required = false) String[] CPP,
                         Model model,
                         Principal principal) throws ParseException, Exception {
        if (principal == null) {
            return "redirect:/login";
        }
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = userService.findByUsername(authentication.getName());//id
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Flight flight = flightService.findById(id);
            if (AFname != null) {
                for (int i = 0; i < AFname.length; i++) {
                    Customer customerSaved = customerService.getCustomerByCodePaper(APaper[i]);
                    if (customerSaved == null){
                        CustomerDto customerDto = new CustomerDto();
                        customerDto.setUser(user);
                        customerDto.setAddress(null);
                        customerDto.setDob(formatter.parse(ADOB[i]));
                        customerDto.setPhoneNum(APhone[i]);
                        customerDto.setFirstName(AFname[i]);
                        customerDto.setLastName(ALname[i]);
                        Customer customer = customerService.save(customerDto);
                        IdPaperDto idPaperDto = new IdPaperDto();
                        idPaperDto.setPaper_type(paperType[i]);
                        idPaperDto.setCode(APaper[i]);
                        idPaperDto.setCustomer(customer);
                        idPaperService.save(idPaperDto);
                        int qty = flightService.getRemainingSeat(id, seat_class);
                        Seat seat = seatService.getSeatBySeatClassAndFlightIdAndSeatTemp(seat_class, (long) qty - i, id);
                        Ticket ticket = ticketService.createTicket(user, customer, seat_class, flight, seat);
                    }else{
                        IdPaperDto idPaperDto = idPaperService.getIdPaperByCustomerId(customerSaved.getId());
                        idPaperService.update(idPaperDto);
                        int qty = flightService.getRemainingSeat(id, seat_class);
                        Seat seat = seatService.getSeatBySeatClassAndFlightIdAndSeatTemp(seat_class, (long) qty - i, id);
                        Ticket ticket = ticketService.createTicket(user, customerSaved, seat_class, flight, seat);
                    }

                }
                Schedule schedule = scheduleService.updateNumOfSeat(seat_class, AFname.length, id);
            }

            if (CFname != null) {
                for (int i = 0; i < CFname.length; i++) {
                    Customer customerSaved = customerService.getCustomerByCodePaper(CPP[i]);
                    if (customerSaved == null) {
                        CustomerDto customerDto = new CustomerDto();
                        customerDto.setUser(user);
                        customerDto.setAddress(null);
                        customerDto.setDob(formatter.parse(CDOB[i]));
                        customerDto.setPhoneNum(CPhone[i]);
                        customerDto.setFirstName(CFname[i]);
                        customerDto.setLastName(CLname[i]);
                        Customer customer = customerService.save(customerDto);
                        IdPaperDto idPaperDto = new IdPaperDto();
                        idPaperDto.setPaper_type("1");
                        idPaperDto.setCode(CPP[i]);
                        idPaperDto.setCustomer(customer);
                        idPaperService.save(idPaperDto);
                        int qty = flightService.getRemainingSeat(id, seat_class);
                        Seat seat = seatService.getSeatBySeatClassAndFlightIdAndSeatTemp(seat_class, (long) qty - i, id);
                        Ticket ticket = ticketService.createTicket(user, customer, seat_class, flight, seat);
                    }else{
                        IdPaperDto idPaperDto = idPaperService.getIdPaperByCustomerId(customerSaved.getId());
                        idPaperService.update(idPaperDto);
                        int qty = flightService.getRemainingSeat(id, seat_class);
                        Seat seat = seatService.getSeatBySeatClassAndFlightIdAndSeatTemp(seat_class, (long) qty - i, id);
                        Ticket ticket = ticketService.createTicket(user, customerSaved, seat_class, flight, seat);
                    }
                }
                Schedule schedule = scheduleService.updateNumOfSeat(seat_class, CFname.length, id);
            }
            return "redirect:/payment";
        }catch(Exception e){
            throw new Exception("Lỗi");
        }
    }

    @GetMapping("/booked/list")
    public String bookedTicket(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(authentication.getName());
        List<Ticket> tickets = ticketService.getAllByUserId(user.getId());
        model.addAttribute("tickets", tickets);
        return "/customer/booked-ticket";
    }

    @GetMapping("/ticket-preview/{id}")
    public String ticketPreview(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        Ticket ticket = ticketService.findById(id);
        Schedule schedule = scheduleService.getByFlightId(ticket.getFlight().getId());
        model.addAttribute("ticket", ticket);
        model.addAttribute("schedule", schedule);
        return "/customer/ticket-preview";
    }
}
