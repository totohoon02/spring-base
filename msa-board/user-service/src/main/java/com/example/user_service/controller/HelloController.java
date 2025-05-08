package com.example.user_service.controller;

import com.example.util.helloUtil.HelloUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		// import from other module
		HelloUtil.hello();
		return "Hello From User App";
	}
}
