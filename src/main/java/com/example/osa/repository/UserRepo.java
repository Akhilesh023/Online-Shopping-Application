package com.example.osa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.osa.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public boolean existsByEmail(String email);

}
