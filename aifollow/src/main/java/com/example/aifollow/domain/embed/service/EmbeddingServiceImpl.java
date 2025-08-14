package com.example.aifollow.domain.embed.service;

import com.example.aifollow.domain.embed.service.inf.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {
	
	// openAiConfig.yml에서 정의
	private final ChatClient chatClient;
	private final EmbeddingModel embeddingModel;
	
	private final VectorStore vectorStore;
	
	@Override
	public float[] embed(String content) {
		float[] embed = embeddingModel.embed(content);
		return embed;
	}
	
	@Override
	public void add(String content) {
		Document document = new Document(content);
		vectorStore.add(List.of(document));
	}
	
	@Override
	public List<Document> search() {
		return vectorStore.similaritySearch("가나다라마바사아자차카타파하abcedfghijklmnopqrstuvwxyz");
	}
	
	@Override
	public String searchWithAdvisor() {
		SearchRequest request = SearchRequest.builder()
				.similarityThreshold(0.5d)
				.topK(5)
				.build();
		
		// 벡터디비를 참고할 수 있게 연결
		Advisor advisor = QuestionAnswerAdvisor.builder(vectorStore)
				.searchRequest(request)
				.build();
		
		return chatClient.prompt(new Prompt("미니언이 하는 말은?"))
				.advisors(advisor)
				.call()
				.content();
	}
}
