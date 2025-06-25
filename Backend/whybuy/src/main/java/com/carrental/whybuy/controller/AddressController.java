package com.carrental.whybuy.controller;

import com.carrental.whybuy.dto.AddressDto;
import com.carrental.whybuy.dto.CustomerDto;
import com.carrental.whybuy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/details/{id}")
    public AddressDto getAddressDetailsById(@PathVariable Long id){
        try {
            AddressDto addressDto=addressService.getCustomerDetailsById(id);
            if(addressDto != null ){
                return addressDto;
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
