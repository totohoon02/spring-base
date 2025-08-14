package com.example.aifollow.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
	
	@Bean
	public ChatMemoryRepository chatMemoryRepository() {
		return new InMemoryChatMemoryRepository(); // Default Value
	}
	
	@Bean
	public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
		return MessageWindowChatMemory.builder()
				.maxMessages(10)
				.chatMemoryRepository(chatMemoryRepository)
				.build();
	}
	
	// Client로 래핑해서 쓸 때 Bean 생성 필요
	@Bean
	ChatClient chatClient(ChatModel chatModel) {
		return ChatClient.create(chatModel);
	}
}
