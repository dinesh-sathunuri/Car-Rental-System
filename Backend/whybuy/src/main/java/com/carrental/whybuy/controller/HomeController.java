package com.carrental.whybuy.controller;

import com.carrental.whybuy.dto.RentalCarDto;
import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.enums.Status;
import com.carrental.whybuy.service.HomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/carlist")
    public List<RentalCarDto> getAllCars(HttpSession session) {
        try {
            List<RentalCarDto> rentalCarDtoList = homeService.getAvailableCars();
            if (rentalCarDtoList != null && !rentalCarDtoList.isEmpty()) {
                return rentalCarDtoList;
            } else {
                return List.of();
            }
        } catch (Exception e) {

            e.printStackTrace();
            return List.of();
        }
    }
    @GetMapping("/carDetails/{id}")
    public RentalCarDto getAllCars(@PathVariable Long id, HttpSession session) {
        try {
            RentalCarDto rentalCarDtoList = homeService.getAvailableCarsById(id);
            if (rentalCarDtoList != null) {
                return rentalCarDtoList;
            } else {
                return null;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/customercars")
    public ResponseEntity<List<RentalCarDto>> getCarsByCustomerId(@RequestBody Map<String, String> body, HttpSession session) {
        try {
            String customerId = body.get("id");
            if (customerId == null || customerId.isBlank()) {
                return ResponseEntity.badRequest().body(List.of());
            }

            List<RentalCarDto> rentalCars = homeService.getCarsByCustomerId(customerId);
            return ResponseEntity.ok(rentalCars);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalCarDto> addCarWithImages(
            @RequestPart("car") RentalCarDto carDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            RentalCarDto savedCar = homeService.saveRentalCarWithImages(carDto, images);
            return ResponseEntity.ok(savedCar);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/requests/car/{carId}")
    public ResponseEntity<List<RentalDto>> getRequestsByCarId(@PathVariable Long carId) {
        try {
            List<RentalDto> rentalRequests = homeService.getRentalRequestsByCarId(carId);
            return ResponseEntity.ok(rentalRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
    @PutMapping("/rentals/{rentalId}/status")
    public ResponseEntity<?> updateRentalStatus(
            @PathVariable Long rentalId,
            @RequestBody Map<String, String> request) {
        try {
            String statusStr = request.get("status");
            Status status = Status.valueOf(statusStr.toUpperCase());

            homeService.updateRentalStatus(rentalId, status);
            return ResponseEntity.ok("Rental status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update rental status.");
        }
    }
    @GetMapping("/rentals/customer/{customerId}")
    public ResponseEntity<List<RentalDto>> getRentalsByCustomer(@PathVariable Long customerId) {
        try {
            List<RentalDto> rentals = homeService.getRentalsByCustomerId(customerId);
            return ResponseEntity.ok(rentals);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}
