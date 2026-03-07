package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.Booking;
import com.hotel.hotel_management.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("rooms", hotelService.getAllRooms());
        return "index";
    }

    @GetMapping("/checkin")
    public String checkInForm(Model model) {
        model.addAttribute("availableRooms", hotelService.getAvailableRooms());
        return "checkin";
    }

    @PostMapping("/checkin")
    public String checkIn(@RequestParam Long roomId,
                          @RequestParam String clientName,
                          @RequestParam Integer days,
                          RedirectAttributes redirectAttributes) {
        try {
            hotelService.checkIn(roomId, clientName, days);
            redirectAttributes.addFlashAttribute("success",
                    "Гость «" + clientName + "» успешно заселён!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkOutForm(Model model) {
        model.addAttribute("occupiedRooms", hotelService.getOccupiedRooms());
        return "checkout";
    }

    @GetMapping("/checkout/details")
    public String checkOutDetails(@RequestParam Long roomId, Model model) {
        Optional<Booking> booking = hotelService.getActiveBookingByRoom(roomId);
        model.addAttribute("occupiedRooms", hotelService.getOccupiedRooms());
        model.addAttribute("selectedRoomId", roomId);
        booking.ifPresent(b -> model.addAttribute("booking", b));
        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkOut(@RequestParam Long roomId,
                           RedirectAttributes redirectAttributes) {
        try {
            BigDecimal total = hotelService.checkOut(roomId);
            redirectAttributes.addFlashAttribute("success",
                    "Гость выселен. Получено: " + total + " ₽");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/history")
    public String history(Model model) {
        List<Booking> bookings = hotelService.getAllBookings();
        model.addAttribute("bookings", bookings);

        long checkedOutCount = bookings.stream()
                .filter(b -> b.getCheckOutDate() != null)
                .count();

        BigDecimal totalRevenue = bookings.stream()
                .filter(b -> b.getCheckOutDate() != null)
                .map(Booking::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("checkedOutCount", checkedOutCount);
        model.addAttribute("totalRevenue", totalRevenue);
        return "history";
    }
}