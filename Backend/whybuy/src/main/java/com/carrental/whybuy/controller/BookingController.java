package com.carrental.whybuy.controller;

import com.carrental.whybuy.dto.BookingRequestDto;
import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.service.BookingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<RentalDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto, HttpSession session) {
        System.out.println(bookingRequestDto);
        try {
            RentalDto createdRental = bookingService.createBooking(bookingRequestDto);
            return ResponseEntity.ok(createdRental);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(null); // Forbidden
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
