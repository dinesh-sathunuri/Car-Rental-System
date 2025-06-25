package com.carrental.whybuy.Mapping;

import com.carrental.whybuy.dto.AddressDto;
import com.carrental.whybuy.dto.CustomerDto;
import com.carrental.whybuy.dto.RentalCarDto;
import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.entity.Address;
import com.carrental.whybuy.entity.Customer;
import com.carrental.whybuy.entity.Rental;
import com.carrental.whybuy.entity.RentalCar;
import com.carrental.whybuy.enums.Role;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    // Entity → DTO
    public static CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setPassword(customer.getPassword());  // Optional: You may want to exclude password for security reasons

        // Convert nested lists using respective mappers
        if (customer.getRentals() != null) {
            List<RentalDto> rentalDtos = customer.getRentals().stream()
                    .map(RentalMapper::toDto)  // Assuming RentalMapper.toDto exists
                    .collect(Collectors.toList());
            dto.setRentals(rentalDtos);
        }

        if (customer.getAddresses() != null) {
            List<AddressDto> addressDtos = customer.getAddresses().stream()
                    .map(AddressMapper::toDto)  // Assuming AddressMapper.toDto exists
                    .collect(Collectors.toList());
            dto.setAddresses(addressDtos);
        }

        if (customer.getRentalCars() != null) {
            List<RentalCarDto> rentalCarDtos = customer.getRentalCars().stream()
                    .map(RentalCarMapper::toDto)  // Assuming RentalCarMapper.toDto exists
                    .collect(Collectors.toList());
            dto.setRentalCars(rentalCarDtos);
        }

        dto.setRole(customer.getRole() == null ? null : customer.getRole().name());
        dto.setLicenseNumber(customer.getLicenseNumber());
        dto.setCreatedAt(customer.getCreatedAt());

        return dto;
    }

    // DTO → Entity
    public static Customer toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setPassword(dto.getPassword());

        // Convert nested lists using respective mappers
        if (dto.getRentals() != null) {
            List<Rental> rentals = dto.getRentals().stream()
                    .map(RentalMapper::toEntity)  // Assuming RentalMapper.toEntity exists
                    .collect(Collectors.toList());
            customer.setRentals(rentals);
        }

        if (dto.getAddresses() != null) {
            List<Address> addresses = dto.getAddresses().stream()
                    .map(AddressMapper::toEntity)  // Assuming AddressMapper.toEntity exists
                    .collect(Collectors.toList());
            customer.setAddresses(addresses);
        }

        if (dto.getRentalCars() != null) {
            List<RentalCar> rentalCars = dto.getRentalCars().stream()
                    .map(RentalCarMapper::toEntity)  // Assuming RentalCarMapper.toEntity exists
                    .collect(Collectors.toList());
            customer.setRentalCars(rentalCars);
        }

        if (dto.getRole() != null) {
            customer.setRole(Role.valueOf(dto.getRole()));  // Converts String to Role enum
        }

        customer.setLicenseNumber(dto.getLicenseNumber());

        return customer;
    }
}
