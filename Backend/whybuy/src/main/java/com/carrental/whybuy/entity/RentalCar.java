package com.carrental.whybuy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "rental_car")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private int year;
    @Column(name = "license_plate")
    private String licensePlate;
    private String description;

    @OneToMany(mappedBy = "rentalCar",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Rental> rentals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "rentalCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarImage> images;

    @Column(name = "rent_per_day", nullable = false)
    private BigDecimal rentPerDay;

    @Column(name = "created_at")
    private final LocalDateTime createdAt= LocalDateTime.now();
}
