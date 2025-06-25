package com.carrental.whybuy.dto;

import com.carrental.whybuy.entity.Rental;
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
public class PaymentDto {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime date;
    private String method;
    private Long rentalId;
}
