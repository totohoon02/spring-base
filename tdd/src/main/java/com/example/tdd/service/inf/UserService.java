package com.example.tdd.service.inf;

import com.example.tdd.domain.User;
import com.example.tdd.service.dto.UserJoinDto;

public interface UserService {
	public User join(UserJoinDto dto);
	
	public User getUser(String username);
}
