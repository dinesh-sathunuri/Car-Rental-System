package com.carrental.whybuy.dto;

import com.carrental.whybuy.entity.Address;
import com.carrental.whybuy.entity.Rental;
import com.carrental.whybuy.entity.RentalCar;
import com.carrental.whybuy.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<RentalDto> rentals;
    private List<AddressDto> addresses;
    private List<RentalCarDto> rentalCars;
    private String role;
    private String licenseNumber;
    private LocalDateTime createdAt;
}
