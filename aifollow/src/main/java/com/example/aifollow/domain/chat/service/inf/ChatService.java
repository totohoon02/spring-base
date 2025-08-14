package com.example.aifollow.domain.chat.service.inf;

import com.example.aifollow.model.CountryInfoDTO;
import reactor.core.publisher.Flux;

public interface ChatService {
	public String chat(String message);
	
	public Flux<String> multiTurnChat(String message);
	
	public CountryInfoDTO structuredOutput(String koName);
	
	public CountryInfoDTO toolCalling(String koName);
}
