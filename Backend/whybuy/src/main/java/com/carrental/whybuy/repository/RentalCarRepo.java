package com.carrental.whybuy.repository;

import com.carrental.whybuy.entity.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalCarRepo extends JpaRepository<RentalCar,Long> {
    List<RentalCar> findByCustomerId(Long customerId);
}
