package com.carrental.whybuy.Mapping;

import com.carrental.whybuy.dto.AddressDto;
import com.carrental.whybuy.entity.Address;
import com.carrental.whybuy.entity.Customer;

public class AddressMapper {

    // Convert Address entity to AddressDto
    public static AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }

        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setCountry(address.getCountry());

        if (address.getCustomer() != null) {
            dto.setCustomerId(address.getCustomer().getId());
        }

        return dto;
    }

    // Convert AddressDto to Address entity
    public static Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());

        if (dto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setId(dto.getCustomerId());
            address.setCustomer(customer);
        }

        return address;
    }
}
