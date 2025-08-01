package ex.springai.service;

import ex.springai.model.ChatEntity;
import ex.springai.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatRepository chatRepository;
	
	@Transactional(readOnly = true)
	public List<ChatEntity> readAllChats(String userId) {
		return chatRepository.findByUserIdOrderByCreatedAtAsc(userId);
	}
}
