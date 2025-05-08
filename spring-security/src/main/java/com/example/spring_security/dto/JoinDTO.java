package com.example.spring_security.dto;

import com.example.spring_security.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String username;
    private String password;

}
