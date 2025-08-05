package com.example.tdd.service;

import com.example.tdd.domain.User;
import com.example.tdd.repository.UserRepository;
import com.example.tdd.service.dto.UserJoinDto;
import com.example.tdd.service.inf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DbUserService implements UserService {
	
	private final UserRepository userRepository;
	
	@Override
	public User join(UserJoinDto dto) {
		log.info("join with UserService: DbUserService");
		User user = new User(dto);
		userRepository.save(user);
		return user;
	}
	
	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username).orElseThrow();
	}
}
