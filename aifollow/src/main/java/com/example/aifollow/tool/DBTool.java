package com.example.aifollow.tool;

import com.example.aifollow.model.CountryInfoDTO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.Map;

public class DBTool {
	
	private static final Map<String, CountryInfoDTO> data = new HashMap<>();
	
	// 클래스가 로딩될 때 한번 실행
	static {
		data.put("대한민국", new CountryInfoDTO("Korea", "Seoul"));
		data.put("일본", new CountryInfoDTO("Japan", "Tokyo"));
		data.put("미국", new CountryInfoDTO("United States", "Washington D.C."));
		data.put("프랑스", new CountryInfoDTO("France", "Paris"));
	}
	
	@Tool(description = "Get Nation Infomation: enName, capital")
	public CountryInfoDTO getDBTool(@ToolParam String koName) {
		return data.getOrDefault(
				koName,
				new CountryInfoDTO("Unknown", "Unknown")
		);
	}
}
