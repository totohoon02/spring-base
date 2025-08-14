package com.example.aifollow.domain.chat.service;

import com.example.aifollow.domain.chat.service.inf.ChatService;
import com.example.aifollow.model.CountryInfoDTO;
import com.example.aifollow.tool.DBTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	
	// Config에 의해 자동 설정
	private final ChatClient chatClient;
	private final ChatMemoryRepository chatMemoryRepository;
	private final ChatMemory chatMemory;
	
	private final String userId = "my-user";
	
	@Override
	public String chat(String message) {
		Prompt prompt = new Prompt(new UserMessage(message));
		return chatClient.prompt(prompt).call().content();
	}
	
	@Override
	public Flux<String> multiTurnChat(String message) {
		chatMemory.add(userId, new UserMessage(message));

		Prompt prompt = new Prompt(chatMemory.get(userId));
		
		// 응답 메시지를 임시 보관
		StringBuilder responseBuffer = new StringBuilder();
		
		return chatClient
				.prompt(prompt)
				.stream()
				.content()                            // Flux<String> 토큰/델타
				.filter(token -> token != null && !token.isBlank())
				.doOnNext(responseBuffer::append)     // 전체 응답 누적
				.doOnComplete(() -> {                 // 스트림 정상 종료 시 대화 저장
					if (!responseBuffer.isEmpty()) {
						chatMemory.add(userId, new AssistantMessage(responseBuffer.toString()));
						chatMemoryRepository.saveAll(userId, chatMemory.get(userId));
					}
				})
				.onErrorResume(ex -> {                // 오류 시 대응 (원하면 커스텀 메시지 전송)
					log.error("LLM streaming error", ex);
					return Mono.empty();
				});
	}
	
	@Override
	public CountryInfoDTO structuredOutput(String koName) {
		SystemMessage systemMessage = new SystemMessage("한국어로 된 국가명을 입력 받아 해당 국가의 공식 영어 명칭과 수도를 반환");
		UserMessage userMessage = new UserMessage(koName);
		
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
		return chatClient.prompt(prompt)
				.call()
				.entity(CountryInfoDTO.class);
	}
	
	@Override
	public CountryInfoDTO toolCalling(String koName) {
		SystemMessage systemMessage = new SystemMessage("한국어로 된 국가명을 입력 받아 해당 국가의 공식 영어 명칭과 수도를 반환");
		UserMessage userMessage = new UserMessage(koName);
		
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
		return chatClient.prompt(prompt)
				.tools(new DBTool())
				.call()  // 동기 호출
				.entity(CountryInfoDTO.class); // 응답을 DTO로 매핑
	}
}
