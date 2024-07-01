package com.example.osa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.osa.entity.Seller;

public interface SellerRepo extends JpaRepository<Seller, Integer> {

}
