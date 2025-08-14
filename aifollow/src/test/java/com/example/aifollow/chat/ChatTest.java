package com.example.aifollow.chat;

import com.example.aifollow.domain.chat.service.inf.ChatService;
import com.example.aifollow.model.CountryInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ChatTest {

	@Autowired
	private ChatService chatService;
	
	@Test
	void 동작_테스트(){
		String message = "안녕하세요?";
		String call = chatService.chat(message);
		log.info(call);
		assertThat(call).isNotEmpty();
	}
	
	@Test
	void 멀티턴_아닌_멀티턴_테스트(){
		chatService.chat("미니언들은 대화를 할 때 각 단어의 첫 글자를 b로 바꿔 예를들면 my name is hoon는 by bame bs boon이라고 말해");
		String response = chatService.chat("그럼 how are you? 는 뭐라고 말할까? 응답만 출력해줘");
		log.info("response: {}", response);
		assertThat(response).isNotEqualTo("bow bar bou");
	}
	
	@Test
	void 멀티턴인_멀티턴_테스트(){
		chatService.multiTurnChat("미니언들은 대화를 할 때 각 단어의 첫 글자를 b로 바꿔 예를들면 my name is hoon는 by bame bs boon이라고 말해")
				.blockLast();
		
		String response = chatService.multiTurnChat("그럼 how are you? 는 뭐라고 말할까? 응답만 출력해줘")
				.collect(Collectors.joining())
				.block();
		
		log.info("response: {}", response);
		assertThat(response).isNotEmpty();
	}
	
	@Test
	void Struectured_Output_테스트(){
		CountryInfoDTO countryInfoDTO = chatService.structuredOutput("미국");
		log.info(countryInfoDTO.toString());
		assertThat(countryInfoDTO.enName()).isEqualTo("United States of America");
	}
	
	@Test
	void 툴_호출_테스트() {
		CountryInfoDTO call1 = chatService.toolCalling("프랑스");
		log.info("call result: {}", call1);
		assertThat(call1.enName()).isEqualTo("France");
		
		CountryInfoDTO call2 = chatService.toolCalling("부카니스탄");
		log.info("call result: {}", call2);
		assertThat(call2.enName()).isEqualTo("Unknown");
	}
}
