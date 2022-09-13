package com.example.junitmockito.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class UserListResponse {

	final List<UserResponse> userResponseList;
}
