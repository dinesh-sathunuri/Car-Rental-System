package com.carrental.whybuy.controller;

import com.carrental.whybuy.dto.CustomerDto;
import com.carrental.whybuy.dto.LoginRequest;
import com.carrental.whybuy.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            Boolean result = customerService.loginUser(loginRequest);
            if (result) {
                return ResponseEntity.ok("Login successful and session created.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDto customerDto){
        try {
            Boolean result = customerService.registerUser(customerDto);

            if(result){
                return ResponseEntity.ok("Register Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists or invalid data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/customerDetails/{id}")
    public CustomerDto getCustomerDetailsById(@PathVariable Long id,HttpSession session){
        try {
            System.out.println(id);
            CustomerDto customerDto=customerService.getCustomerDetailsById(id);
            if(customerDto != null ){
                return customerDto;
            }else {
                return new CustomerDto();
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CustomerDto();
        }
    }
    @PostMapping("/by-email")
    public ResponseEntity<?> getCustomerByEmail(@RequestBody Map<String, String> body) {
        System.out.println( body.get("email"));
        try {
            String email = body.get("email");
            CustomerDto customerDto= customerService.getCustomerByEmail(email);
            return ResponseEntity.ok(customerDto);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Customer not found");
        }
    }






}
