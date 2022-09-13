package com.example.junitmockito.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class SignUpRequest {

	private final String email;

	private final String pw;
}
