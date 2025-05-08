package com.example.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring_security.dto.JoinDTO;
import com.example.spring_security.service.JoinService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;

	@GetMapping("/join")
	public String joinP() {
		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProcess(JoinDTO joinDTO) {
		// form으로 받을 떄 에너테이션 생략 가능, 자동으로 @ModelAttribute 들어간다.
		joinService.join(joinDTO);
		return "redirect:/login";
	}
}
