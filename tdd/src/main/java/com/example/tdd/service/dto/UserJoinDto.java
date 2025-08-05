package com.example.tdd.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserJoinDto {
	
	private final String username;
	private final String password;
	@Setter
	private String role;
	
	
	public UserJoinDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
}
