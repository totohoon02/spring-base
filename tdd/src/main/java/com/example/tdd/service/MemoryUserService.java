package com.example.tdd.service;

import com.example.tdd.domain.User;
import com.example.tdd.service.dto.UserJoinDto;
import com.example.tdd.service.inf.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

//@Service
@Slf4j
public class MemoryUserService implements UserService {
	
	private final Map<String, User> users = new HashMap<>();
	public Long sequence = 0L;
	
	@Override
	public User join(UserJoinDto dto) {
		log.info("join with UserService: MemoryUserService");
		User user = new User(++sequence, dto);
		users.put(dto.getUsername(), user);
		return user;
	}
	
	@Override
	public User getUser(String username) {
		return users.get(username);
	}
}
