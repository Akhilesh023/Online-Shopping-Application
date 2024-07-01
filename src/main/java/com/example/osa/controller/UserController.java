package com.example.osa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.osa.enums.UserRole;
import com.example.osa.requestdto.OtpVerificationRequest;
import com.example.osa.requestdto.UserRequest;
import com.example.osa.responsedto.UserResponse;
import com.example.osa.service.UserService;
import com.example.osa.util.ResponseStructure;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/sellers/register")
	public ResponseEntity<ResponseStructure<UserResponse>> addSeller(@RequestBody UserRequest userRequest){
		return userService.addUser(userRequest, UserRole.SELLER);
	}
	@PostMapping("/customers/register")
	public ResponseEntity<ResponseStructure<UserResponse>> addCustomer(@RequestBody UserRequest userRequest){
		return userService.addUser(userRequest, UserRole.CUSTOMER);
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(@RequestBody OtpVerificationRequest otpVerificationRequest){
		return userService.verifyOtp(otpVerificationRequest);
	}
	
	

}
