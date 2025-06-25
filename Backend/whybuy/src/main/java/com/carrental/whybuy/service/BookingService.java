package com.carrental.whybuy.service;

import com.carrental.whybuy.Mapping.RentalMapper;
import com.carrental.whybuy.dto.BookingRequestDto;
import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.entity.Customer;
import com.carrental.whybuy.entity.Payment;
import com.carrental.whybuy.entity.Rental;
import com.carrental.whybuy.entity.RentalCar;
import com.carrental.whybuy.enums.Status;
import com.carrental.whybuy.repository.CustomerRepo;
import com.carrental.whybuy.repository.PaymentRepo;
import com.carrental.whybuy.repository.RentalCarRepo;
import com.carrental.whybuy.repository.RentalRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final RentalRepo rentalRepository;
    private final CustomerRepo customerRepository;
    private final RentalCarRepo rentalCarRepository;
    private final RentalMapper rentalMapper;

    private final  PaymentRepo paymentRepo;

    @Transactional
    public RentalDto createBooking(@NotNull BookingRequestDto bookingRequestDto) {
        Customer customer = customerRepository.findById(bookingRequestDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        RentalCar rentalCar = rentalCarRepository.findById(bookingRequestDto.getRentalCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        if (customer.getId().equals(rentalCar.getCustomer().getId())) {
            throw new IllegalStateException("You cannot rent your own car");
        }

        long days = Duration.between(bookingRequestDto.getStartDate(), bookingRequestDto.getEndDate()).toDays();
        if (days <= 0) throw new IllegalArgumentException("Invalid date range");

        BigDecimal totalAmount = rentalCar.getRentPerDay().multiply(BigDecimal.valueOf(days));

        Rental rental= Rental.builder()
                .startDate(bookingRequestDto.getStartDate())
                .endDate(bookingRequestDto.getEndDate())
                .status(Status.PENDING)
                .totalAmount(bookingRequestDto.getTotalAmount())
                .customer(customer)
                .rentalCar(rentalCar)
                .build();
        Rental saved = rentalRepository.save(rental);

        Payment payment = Payment.builder()
                .amount(totalAmount)
                .method(bookingRequestDto.getMethod())
                .date(LocalDateTime.now())
                .rental(saved)
                .build();
        paymentRepo.save(payment);



        return rentalMapper.toDto(saved);
    }
}
