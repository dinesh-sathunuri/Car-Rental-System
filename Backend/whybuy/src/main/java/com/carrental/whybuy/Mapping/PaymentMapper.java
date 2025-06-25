package com.carrental.whybuy.Mapping;

import com.carrental.whybuy.dto.PaymentDto;
import com.carrental.whybuy.entity.Payment;
import com.carrental.whybuy.entity.Rental;

public class PaymentMapper {

    public static PaymentDto toDto(Payment payment) {
        if (payment == null) return null;

        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setDate(payment.getDate());
        dto.setMethod(payment.getMethod());

        if (payment.getRental() != null) {
            dto.setRentalId(payment.getRental().getId());
        }

        return dto;
    }

    public static Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate());
        payment.setMethod(dto.getMethod());

        if (dto.getRentalId() != null) {
            Rental rental = new Rental();
            rental.setId(dto.getRentalId());
            payment.setRental(rental);
        }

        return payment;
    }
}
