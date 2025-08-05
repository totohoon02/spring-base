package com.example.tdd.domain;

import com.example.tdd.service.dto.UserJoinDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String role;
	
	
	public User(Long id, UserJoinDto dto) {
		this.id = id;
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.role = dto.getRole();
	}
	
	public User(UserJoinDto dto) {
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.role = dto.getRole();
	}
}
