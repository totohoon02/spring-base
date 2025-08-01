# Rate Limiter & Redis

## Redis Configuration

- RedisConfig에서 생성한 `RedisTemplate`은 `redisConfig.yml`의 값을 읽지 않음
- `host`와 `port`를 적용하기 위해서는 값을 가져와서 `RedisStandaloneConfiguration`를 사용한다.
- `JPA`로 사용할 때는 `redisConfig.yml`를 참조함 
- Serializer 따로 정의

## Rate Limiter

- 요청 횟수 초과 시 요청을 차단
- `*Application.java` -> `@EnableAspectJAutoProxy` 
- Redis로 key-expire 사용해 구현
- `	@Around("@annotation(RateLimited) && args(key,..)")` 파라미터 일치 필요