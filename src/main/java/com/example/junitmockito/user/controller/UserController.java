package com.example.junitmockito.user.controller;

import com.example.junitmockito.user.domain.dto.SignUpRequest;
import com.example.junitmockito.user.domain.dto.UserListResponse;
import com.example.junitmockito.user.domain.dto.UserResponse;
import com.example.junitmockito.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/signUp")
	public ResponseEntity<UserResponse> signUp(final SignUpRequest signUpRequest) {

		final UserResponse userResponse = userService.signUp(signUpRequest);

		return ResponseEntity.ok().body(userResponse);
	}

	@GetMapping("/list")
	public ResponseEntity<UserListResponse> getUserList() {

		return ResponseEntity.ok(userService.getUserList());
	}
}
