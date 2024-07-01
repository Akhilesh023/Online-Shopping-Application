package com.example.osa.serviceImpli;

import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.osa.entity.Customer;
import com.example.osa.entity.Seller;
import com.example.osa.entity.User;
import com.example.osa.enums.UserRole;
import com.example.osa.mapper.UserMapper;
import com.example.osa.repository.UserRepo;
import com.example.osa.requestdto.OtpVerificationRequest;
import com.example.osa.requestdto.UserRequest;
import com.example.osa.responsedto.UserResponse;
import com.example.osa.service.UserService;
import com.example.osa.util.MessageData;
import com.example.osa.util.ResponseStructure;
import com.google.common.cache.Cache;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpli implements UserService {

	private final MessageData messageData;

	private final MailService mailService;

	private final UserRepo userRepo;

	private final Cache<String, User> userCache;

	private final Cache<String, String> otpCache;

	private UserMapper userMapper;

	private final Random random;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, UserRole userRole) {

		User user = null;

		switch (userRole){
		case SELLER -> user = new Seller();
		case CUSTOMER -> user = new Customer();
		}

		if(user != null) {
			user = userMapper.mapToUser(userRequest, user);
			user.setUserRole(userRole);
		}

		int number = random.nextInt(100000,999999);
		String numberStr = String.valueOf(number);

		userCache.put(user.getEmail(), user);
		otpCache.put(user.getEmail(), numberStr);



		messageData.setTo(user.getEmail());
		messageData.setSubject("Your OTP for your Account");
		messageData.setText(numberStr);
		messageData.setSentDate(new Date(System.currentTimeMillis()));



		try {
			mailService.sendMail(messageData);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println(number);
		System.out.println(user);
		System.out.println(userCache.getIfPresent(user.getEmail()));
		System.out.println(otpCache.getIfPresent(user.getEmail()));

		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new ResponseStructure<UserResponse>()
						.setData(userMapper.mapToUserResponse(user))
						.setStatus(HttpStatus.ACCEPTED.value())
						.setMessage("User Accepeted"));

	}

	public String extractUsername(String email)
	{
		if (email == null || !email.contains("@gmail.com")) {
			throw new IllegalArgumentException("Invalid Gmail address");
		}

		int atIndex = email.indexOf("@");
		if (atIndex == -1) {
			throw new IllegalArgumentException("Invalid email address format");
		}

		return email.substring(0, atIndex);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpVerificationRequest otpVerificationRequest) {

		User user = userCache.getIfPresent(otpVerificationRequest.getEmail());
		String otp = otpCache.getIfPresent(otpVerificationRequest.getEmail());

		if(user == null || otp == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseStructure<UserResponse>()
							.setData(userMapper.mapToUserResponse(user))
							.setStatus(HttpStatus.NOT_FOUND.value())
							.setMessage("Invalid OTP or User not Found"));
		}

		if(otpVerificationRequest.getOtp().equals(otp)) {

			//save operation

			String name =extractUsername(otpVerificationRequest.getEmail());

			user.setUsername(name);
			user.setEmailVerified(true);
			userRepo.save(user);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseStructure<UserResponse>()
							.setData(userMapper.mapToUserResponse(user))
							.setStatus(HttpStatus.CREATED.value())
							.setMessage("User data is saved"));	
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseStructure<UserResponse>()
							.setData(userMapper.mapToUserResponse(user))
							.setStatus(HttpStatus.NOT_FOUND.value())
							.setMessage("Invalid OTP"));
		}	
	}	
}