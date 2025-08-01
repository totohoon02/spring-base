package ex.springai.controller;

import ex.springai.model.ChatDTO;
import ex.springai.model.ChatEntity;
import ex.springai.service.ChatService;
import ex.springai.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
	
	private final OpenAIService openAIService;
	private final ChatService chatService;
	
	@PostMapping("/chat")
	public Flux<String> chat(@RequestBody ChatDTO chatDTO) {
		return openAIService.generateStream(chatDTO.getText());
	}
	
	@PostMapping("/chat/history/{userid}")
	public List<ChatEntity> getChatHistory(@PathVariable("userid") String userId) {
		return chatService.readAllChats(userId);
	}
}
