package com.carrental.whybuy.Mapping;

import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.entity.Payment;
import com.carrental.whybuy.entity.Rental;
import com.carrental.whybuy.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    // Entity → DTO
    public static RentalDto toDto(Rental rental) {
        if (rental == null) {
            return null;
        }

        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setStartDate(rental.getStartDate());
        dto.setEndDate(rental.getEndDate());
        dto.setTotalAmount(rental.getTotalAmount());
        dto.setStatus(rental.getStatus() == null ? null : rental.getStatus().name());
        dto.setCreatedAt(rental.getCreatedAt());

        if (rental.getCustomer() != null) {
            dto.setCustomerId(rental.getCustomer().getId());
        }
        if (rental.getRentalCar() != null) {
            dto.setRentalCarId(rental.getRentalCar().getId());
        }
        if (rental.getPayment() != null) {
            Payment payment = rental.getPayment();
            dto.setPaymentId(payment.getId());
        }

        return dto;
    }

    // DTO → Entity
    public static Rental toEntity(RentalDto dto) {
        if (dto == null) {
            return null;
        }

        Rental rental = new Rental();
        rental.setId(dto.getId());
        rental.setStartDate(dto.getStartDate());
        rental.setEndDate(dto.getEndDate());
        rental.setTotalAmount(dto.getTotalAmount());

        if (dto.getStatus() != null) {
            rental.setStatus(Status.valueOf(dto.getStatus()));
        }


        // For associations like Customer, RentalCar, and Payment, you can create minimal stub objects
        // with only IDs set, or handle them via service layers depending on your app design.

        if (dto.getCustomerId() != null) {
            var customer = new com.carrental.whybuy.entity.Customer();
            customer.setId(dto.getCustomerId());
            rental.setCustomer(customer);
        }

        if (dto.getRentalCarId() != null) {
            var rentalCar = new com.carrental.whybuy.entity.RentalCar();
            rentalCar.setId(dto.getRentalCarId());
            rental.setRentalCar(rentalCar);
        }

        if (dto.getPaymentId() != null) {
            var payment = new Payment();
            payment.setId(dto.getPaymentId());
            rental.setPayment(payment);
        }

        return rental;
    }
}
