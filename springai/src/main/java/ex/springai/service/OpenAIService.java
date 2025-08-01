package ex.springai.service;

import ex.springai.model.ChatEntity;
import ex.springai.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIService {
	
	private final ChatClient chatClient;
	private final OpenAiEmbeddingModel openAiEmbeddingModel;
	
	private final ChatMemoryRepository chatMemoryRepository;
	private final ChatRepository chatRepository;
	
	public String generate(String text) {
		SystemMessage systemMessage = new SystemMessage("");
		UserMessage userMessage = new UserMessage(text);
		AssistantMessage assistantMessage = new AssistantMessage("");
		
		OpenAiChatOptions options = OpenAiChatOptions.builder()
				.model("gpt-4.1-mini")
				.temperature(0.7)
				.build();
		
		// 프롬프트
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage, assistantMessage), options);
		
		// 요청 및 응답
		return chatClient.prompt(prompt).call().content();
	}
	
	public Flux<String> generateStream(String text) {
		// 유저&페이지별 ChatMemory를 관리하기 위한 key (우선은 명시적으로)
		String userId = "xxxjjhhh" + "_" + "4";
		
		// 전체 대화 저장용
		ChatEntity chatUserEntity = new ChatEntity();
		chatUserEntity.setUserId(userId);
		chatUserEntity.setType(MessageType.USER);
		chatUserEntity.setContent(text);
		
		// 메시지
		ChatMemory chatMemory = MessageWindowChatMemory.builder()
				.maxMessages(10)
				.chatMemoryRepository(chatMemoryRepository)
				.build();
		chatMemory.add(userId, new UserMessage(text)); // 신규 메시지도 추가
		
		// 옵션
		OpenAiChatOptions options = OpenAiChatOptions.builder()
				.model("gpt-4.1-mini")
				.temperature(0.7)
				.build();
		
		// 프롬프트
		Prompt prompt = new Prompt(chatMemory.get(userId), options);
		
		// 응답 메시지를 저장할 임시 버퍼
		StringBuilder responseBuffer = new StringBuilder();
		
		// 요청 및 응답
		return chatClient.prompt(prompt)
//				.tools(new ChatTools())
//				.advisors()
				.stream()
				.content()
				.map(token -> {
					responseBuffer.append(token);
					return token;
				})
				.doOnComplete(() -> {
					// chatMemory 저장
					chatMemory.add(userId, new AssistantMessage(responseBuffer.toString()));
					chatMemoryRepository.saveAll(userId, chatMemory.get(userId));
					
					// 전체 대화 저장용
					ChatEntity chatAssistantEntity = new ChatEntity();
					chatAssistantEntity.setUserId(userId);
					chatAssistantEntity.setType(MessageType.ASSISTANT);
					chatAssistantEntity.setContent(responseBuffer.toString());
					
					chatRepository.saveAll(List.of(chatUserEntity, chatAssistantEntity));
				});
	}
	
	public List<float[]> generateEmbedding(List<String> texts, String model) {
		
		// 옵션
		EmbeddingOptions embeddingOptions = OpenAiEmbeddingOptions.builder()
				.model(model).build();
		
		// 프롬프트
		EmbeddingRequest prompt = new EmbeddingRequest(texts, embeddingOptions);
		
		// 요청 및 응답
		EmbeddingResponse response = openAiEmbeddingModel.call(prompt);
		return response.getResults().stream()
				.map(Embedding::getOutput)
				.toList();
	}
}
