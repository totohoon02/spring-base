package com.example.aifollow.domain.embed.service.inf;

import org.springframework.ai.document.Document;

import java.util.List;

public interface EmbeddingService {
	float[] embed(String content);
	
	void add(String content);
	
	List<Document> search();
	
	String searchWithAdvisor();
}
