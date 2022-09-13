package com.example.junitmockito.user.service;

import com.example.junitmockito.user.domain.dto.SignUpRequest;
import com.example.junitmockito.user.domain.dto.UserListResponse;
import com.example.junitmockito.user.domain.dto.UserResponse;
import com.example.junitmockito.user.domain.entity.User;
import com.example.junitmockito.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService target;

	@Mock
	private UserRepository userRepository;

	@Test
	public void 사용자등록성공() {
	    // given
		SignUpRequest signUpRequest = signUpRequest();

		doReturn(User.builder().email(signUpRequest.getEmail()).pw(signUpRequest.getPw()).build())
			.when(userRepository).save(any(User.class));

		// when
		UserResponse user = target.signUp(signUpRequest);
	    
	    // then
		assertThat(user.getEmail()).isEqualTo(signUpRequest.getEmail());
		assertThat(user.getPw()).isEqualTo(signUpRequest.getPw());
	}

	@Test
	public void 사용자목록조회성공() {
		doReturn(userList()).when(userRepository).findAll();

		final UserListResponse userListResponse = target.getUserList();

		assertThat(userListResponse.getUserResponseList().size()).isEqualTo(5);
	}

	private List<User> userList() {
		List<User> userList = new ArrayList<>();
		for (int i=0; i<5; i++) {
			userList.add(User.builder().build());
		}
		return userList;
	}

	public SignUpRequest signUpRequest() {
		return SignUpRequest.builder()
			.email("test@test.test")
			.pw("test")
			.build();
	}
}