package com.example.osa.mapper;

import org.springframework.stereotype.Component;

import com.example.osa.entity.User;
import com.example.osa.requestdto.UserRequest;
import com.example.osa.responsedto.UserResponse;
@Component
public class UserMapper {
	
	public User mapToUser(UserRequest userRequest, User user) {
		
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		return user;
	}
	
	public UserResponse mapToUserResponse(User user) {
		
		return UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.password(user.getPassword())
				.email(user.getEmail())
				.isEmailVerified(user.isEmailVerified())
				.isDeleted(user.isDeleted())
				.userRole(user.getUserRole())
				.build();
		
		
	}

}
