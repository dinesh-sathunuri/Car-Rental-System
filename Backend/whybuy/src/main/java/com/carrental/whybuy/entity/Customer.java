package com.carrental.whybuy.entity;

import com.carrental.whybuy.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is Required")
    private String email;
    private String password;
    @Column(name = "phone_number")
    @NotBlank(message = "Phone number is Required")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<RentalCar> rentalCars;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "license_number")
    @NotBlank(message = "license number is required")
    private String licenseNumber;

    @Column(name = "created_at")
    private final LocalDateTime createdAt= LocalDateTime.now();

}
