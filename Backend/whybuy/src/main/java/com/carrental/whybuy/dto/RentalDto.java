package com.carrental.whybuy.dto;

import com.carrental.whybuy.entity.Customer;
import com.carrental.whybuy.entity.Payment;
import com.carrental.whybuy.entity.RentalCar;
import com.carrental.whybuy.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal totalAmount;
    private String status;
    private Long customerId;
    private Long rentalCarId;
    private Long paymentId;
    private LocalDateTime createdAt;
}
