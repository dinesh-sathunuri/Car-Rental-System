package com.carrental.whybuy.service;

import com.carrental.whybuy.Mapping.CustomerMapper;
import com.carrental.whybuy.dto.CustomerDto;
import com.carrental.whybuy.dto.LoginRequest;
import com.carrental.whybuy.entity.Customer;
import com.carrental.whybuy.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Boolean loginUser(LoginRequest loginRequest) {
        Optional<Customer> optionalCustomer=customerRepo.findByEmail(loginRequest.getEmail());
        if(optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (passwordEncoder.matches(loginRequest.getPassword(),customer.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public Boolean registerUser(CustomerDto customerDto) {
        Optional<Customer> existingCustomer = customerRepo.findByEmail(customerDto.getEmail());
        if (existingCustomer.isPresent()) {
            return false; // Email already in use
        }

        // 2. Encode password
        String encodedPassword = passwordEncoder.encode(customerDto.getPassword());
        customerDto.setPassword(encodedPassword);

        // 3. Set default role if null
        if (customerDto.getRole() == null) {
            customerDto.setRole("Customer");
        }

        // 4. Map DTO to Entity
        Customer customer = CustomerMapper.toEntity(customerDto);
        System.out.println(customer);
        // 5. Save to DB
        customerRepo.save(customer);
        return true;
    }

    public CustomerDto getCustomerDetailsById(Long id) {
        return customerRepo.findById(id)
                .map(CustomerMapper::toDto)
                .orElse(null);
    }

    public CustomerDto getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email)
                .map(CustomerMapper::toDto)
                .orElse(null);
    }
}
