package com.example.osa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.osa.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
