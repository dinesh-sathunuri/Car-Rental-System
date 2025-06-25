package com.carrental.whybuy.service;

import com.carrental.whybuy.Mapping.AddressMapper;
import com.carrental.whybuy.Mapping.RentalCarMapper;
import com.carrental.whybuy.Mapping.RentalMapper;
import com.carrental.whybuy.dto.RentalCarDto;
import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.entity.CarImage;
import com.carrental.whybuy.entity.Customer;
import com.carrental.whybuy.entity.Rental;
import com.carrental.whybuy.entity.RentalCar;
import com.carrental.whybuy.enums.Status;
import com.carrental.whybuy.repository.CustomerRepo;
import com.carrental.whybuy.repository.RentalCarRepo;
import com.carrental.whybuy.repository.RentalRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final RentalCarRepo rentalCarRepo;
    private final CustomerRepo customerRepo;
    private final RentalCarMapper rentalCarMapper;
    private final RentalRepo rentalRepo;

    public List<RentalCarDto> getAvailableCars() {
        List<RentalCar> allCars = rentalCarRepo.findAll(); // fetch all cars

        return allCars.stream()
                .map(RentalCarMapper::toDto)
                .collect(Collectors.toList());
    }

    private boolean isCarAvailable(RentalCar car) {
        return car.getRentals() == null || car.getRentals().isEmpty();
    }

    public RentalCarDto getAvailableCarsById(Long id) {
        return rentalCarRepo.findById(id)
                .map(RentalCarMapper::toDto)
                .orElse(null);
    }

    public List<RentalCarDto> getCarsByCustomerId(String id) {
        Long customerId = Long.parseLong(id);
        List<RentalCar> rentalCars = rentalCarRepo.findByCustomerId(customerId);
        return rentalCars.stream()
                .map(RentalCarMapper::toDto)
                .collect(Collectors.toList());
    }
    public RentalCarDto saveRentalCarWithImages(RentalCarDto dto, List<MultipartFile> images) throws IOException {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        RentalCar rentalCar = RentalCarMapper.toEntity(dto);
        rentalCar.setCustomer(customer);

        List<CarImage> carImages = new ArrayList<>();

        for (MultipartFile file : images) {
            CarImage image = CarImage.builder()
                    .imageData(file.getBytes())
                    .rentalCar(rentalCar)
                    .build();
            carImages.add(image);
        }

        rentalCar.setImages(carImages);

        RentalCar savedCar = rentalCarRepo.save(rentalCar);

        // Convert saved entity back to DTO
        RentalCarDto responseDto = RentalCarMapper.toDto(savedCar);
        return responseDto;
    }

    public List<RentalDto> getRentalRequestsByCarId(Long carId) {
        return rentalRepo.findByRentalCarId(carId).
                stream().
                map(RentalMapper::toDto).
                collect(Collectors.toList());
    }

    public void updateRentalStatus(Long rentalId, Status status) {
        Rental rental = rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + rentalId));
        rental.setStatus(status);
        rentalRepo.save(rental);
    }

    public List<RentalDto> getRentalsByCustomerId(Long customerId) {
        return rentalRepo.findByCustomerId(customerId).stream()
                .map(RentalMapper::toDto)
                .collect(Collectors.toList());
    }
}
