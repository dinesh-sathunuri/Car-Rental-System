package com.carrental.whybuy.service;

import com.carrental.whybuy.Mapping.AddressMapper;
import com.carrental.whybuy.dto.AddressDto;
import com.carrental.whybuy.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepo addressRepo;
    public AddressDto getCustomerDetailsById(Long id) {
        return addressRepo.findById(id)
                .map(AddressMapper::toDto)
                .orElse(null);
    }
}
