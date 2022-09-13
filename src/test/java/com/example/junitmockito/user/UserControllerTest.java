package com.example.junitmockito.user;

import com.example.junitmockito.user.controller.UserController;
import com.example.junitmockito.user.domain.dto.SignUpRequest;
import com.example.junitmockito.user.domain.dto.UserListResponse;
import com.example.junitmockito.user.domain.dto.UserResponse;
import com.example.junitmockito.user.service.UserService;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController target;

	@Mock
	UserService userService;

	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}
	
	@Test
	public void 회원가입성공테스트() throws Exception {
	    // given
		SignUpRequest signUpRequest = signUpRequest();
		UserResponse userResponse = userResponse();

		doReturn(userResponse).when(userService).signUp(any(SignUpRequest.class));
	    
	    // when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/signUp")
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(signUpRequest))
		);
	    
	    // then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("email", userResponse.getEmail()).exists())
			.andExpect(jsonPath("pw", userResponse.getPw()).exists());
	}
	
	@Test
	public void 사용자목록조회테스트() throws Exception {
	    // given
		doReturn(userResponseList()).when(userService).getUserList();
	    
	    // when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/list"));
	    
	    // then
		MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();

		UserListResponse userListResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(), UserListResponse.class);
		assertThat(userListResponse.getUserResponseList().size()).isEqualTo(5);
	}

	public SignUpRequest signUpRequest() {
		return SignUpRequest.builder()
			.email("test@test.test")
			.pw("test")
			.build();
	}

	public UserResponse userResponse() {
		return UserResponse.builder()
			.email("test@test.test")
			.pw("test")
			.build();
	}

	public UserListResponse userResponseList() {
		List<UserResponse> userResponseList = new ArrayList<>();
		for(int i=0; i<5; i++) {
			userResponseList.add(UserResponse.builder().build());
		}

		return UserListResponse.builder().userResponseList(userResponseList).build();
	}
}