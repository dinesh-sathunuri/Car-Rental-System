package com.carrental.whybuy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalCarDto {
    private Long id;

    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private String description;
    private List<RentalDto> rentals;
    private List<CarImageDto> images;
    private Long customerId;
    private BigDecimal rentPerDay;
    private LocalDateTime createdAt;

}
