package com.example.user;


import com.example.tdd.domain.User;
import com.example.tdd.service.dto.UserJoinDto;
import com.example.tdd.service.inf.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserJoinTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void 회원가입(){
		// given
		UserJoinDto dto = new UserJoinDto("kang", "password");
		dto.setRole("USER");
		
		// when
		User user = userService.join(dto);
		
		// then
		User findUser = userService.getUser(dto.getUsername());
		
		assertThat(findUser.getUsername()).isEqualTo(dto.getUsername());
	}
}
