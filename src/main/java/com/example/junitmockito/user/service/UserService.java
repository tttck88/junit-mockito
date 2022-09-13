package com.example.junitmockito.user.service;

import com.example.junitmockito.user.domain.dto.SignUpRequest;
import com.example.junitmockito.user.domain.dto.UserListResponse;
import com.example.junitmockito.user.domain.dto.UserResponse;
import com.example.junitmockito.user.domain.entity.User;
import com.example.junitmockito.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	public UserResponse signUp(final SignUpRequest signUpRequest) {
		final User user = User.builder()
			.email(signUpRequest.getEmail())
			.pw(signUpRequest.getPw())
			.build();

		User result = userRepository.save(user);

		return UserResponse.builder()
			.email(result.getEmail())
			.pw(result.getPw())
			.build();
	}

	public UserListResponse getUserList() {
		List<UserResponse> userResponseList = userRepository.findAll().stream()
			.map(user -> UserResponse.builder().email(user.getEmail()).pw(user.getPw()).build())
			.collect(Collectors.toList());

		return UserListResponse.builder().userResponseList(userResponseList).build();
	}
}
