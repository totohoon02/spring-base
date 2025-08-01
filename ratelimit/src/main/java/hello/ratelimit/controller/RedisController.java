package hello.ratelimit.controller;

import hello.ratelimit.aop.limiter.RateLimited;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
	
	private final RedisTemplate<String, String> redisTemplate;
	
	@PostMapping
	@RateLimited
	public String setKey(@RequestParam String key) {
		redisTemplate.opsForValue().set("foo", "b12412412a2r");
		return "hello";
	}

}
