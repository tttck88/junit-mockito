package com.example.junitmockito.user.repository;

import com.example.junitmockito.user.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void 사용자추가() {
	    // given
		User user = user();

		// when
		User savedUser = userRepository.save(user);

		// then
		assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(savedUser.getPw()).isEqualTo(user.getPw());
	}

	@Test
	public void 사용자목록조회() {
		// given
		userRepository.save(user());

		// when
		List<User> userList = userRepository.findAll();

		// then
		assertThat(userList.size()).isEqualTo(1);
	}

	private User user() {
		return User.builder()
			.email("email")
			.pw("pw")
			.build();
	}
}