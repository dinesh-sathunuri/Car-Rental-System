package com.carrental.whybuy.repository;

import com.carrental.whybuy.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
}
