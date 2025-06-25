package com.carrental.whybuy.repository;

import com.carrental.whybuy.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepo extends JpaRepository<Rental,Long> {
    List<Rental> findByRentalCarId(Long carId);

    List<Rental> findByCustomerId(Long customerId);
}
