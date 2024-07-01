package com.example.osa.service;

import org.springframework.http.ResponseEntity;

import com.example.osa.enums.UserRole;
import com.example.osa.requestdto.OtpVerificationRequest;
import com.example.osa.requestdto.UserRequest;
import com.example.osa.responsedto.UserResponse;
import com.example.osa.util.ResponseStructure;


public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, UserRole userRole);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpVerificationRequest otpVerificationRequest);


}
