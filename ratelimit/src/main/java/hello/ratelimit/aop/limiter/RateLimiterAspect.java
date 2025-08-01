package hello.ratelimit.aop.limiter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimiterAspect {
	
	private final RateLimiter rateLimiter;
	
	// key를 파라미터로 받는 메서드
	@Around("@annotation(RateLimited) && args(key,..)")
	public Object checkRateLimit(ProceedingJoinPoint joinPoint, String key) throws Throwable {
		log.info("Rate limit AOP hit for key: {}", key);
		
		if (!rateLimiter.isAllowed(key)) {
			throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
		}
		
		return joinPoint.proceed();
	}
}
