package com.example.post_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class HelloController {

	@GetMapping
	public String hello() {
		return "Hello From Post App";
	}
}
