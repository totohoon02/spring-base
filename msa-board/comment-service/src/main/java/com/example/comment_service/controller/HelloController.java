package com.example.comment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class HelloController {

	@GetMapping
	public String hello() {
		return "Hello From Comment App";
	}
}
