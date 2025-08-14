package com.example.aifollow.embed;

import com.example.aifollow.domain.embed.service.inf.EmbeddingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class EmbeddingTest {
	
	@Autowired
	private EmbeddingService embeddingService;
	
	@Test
	void 임베딩_테스트() {
		float[] embed = embeddingService.embed("안녕하세요");
		log.info("vector= {}", embed);
		assertThat(embed).hasSize(1536);
	}
	
	@Test
	void 데이터_추가_테스트() {
		// 한번만 실행
		embeddingService.add("미니언이 하는 말은 '빠까뽀'이다.");
	}
	
	@Test
	void 검색_테스트() {
		List<Document> documents = embeddingService.search();
		log.info("search: {}", documents);
		assertThat(documents).isNotEmpty();
	}
	
	@Test
	void 어드바이저_테스트() {
		String response = embeddingService.searchWithAdvisor();
		log.info("response: {}", response);
	}
}
