package hello.ratelimit.aop.limiter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimiter {
	
	private final StringRedisTemplate redisTemplate;
	private static final int LIMIT = 5;
	private static final long DURATION = 5;
	
	public boolean isAllowed(String key) {
		String redisKey = "rate-limit:" + key;
		Long count = redisTemplate.opsForValue().increment(redisKey); // 없으면 1로 초기화
		
		if (count == 1) {
			redisTemplate.expire(redisKey, Duration.ofSeconds(DURATION));
		}
		
		return count <= LIMIT;
	}
}
