package com.carrental.whybuy.entity;

import com.carrental.whybuy.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private RentalCar rentalCar;

    @Column(name = "created_at")
    private final LocalDateTime createdAt= LocalDateTime.now();

    @OneToOne(mappedBy = "rental",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Payment payment;

}
