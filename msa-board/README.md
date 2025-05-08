# Spring MSA

## Modules
- discovery-service (Eureka Server)
- api-gateway (Eureka Client, Gateway)
- user-service (Eureka Client)
- post-service (Eureka Client)
- comment-service (Eureka Client)
- util (Common Module)

## settings.gradle
```yaml
rootProject.name = 'msa-board'
include 'user-service', 'post-service', 'comment-service', 'api-gateway', 'discovery-service', 'util'
```
- 루트 프로젝트에만 존재해야 한다.
- 다른 모듈에 있는 파일 삭제

## build.gradle에 추가
```yaml
tasks.register('prepareKotlinBuildScriptModel') {

}
```
## Cautions
- 스프링부트 스타터로 패키지 추가하면 잘못된 패키지가 추가된다.
```yaml
implementation 'org.springframework.cloud:spring-cloud-starter-gateway-mvc' # X
implementation 'org.springframework.cloud:spring-cloud-starter-gateway' # O
```
- api-gateway는 netty 기반 webflux 패키지 추가
```yaml
implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
implementation 'org.springframework.boot:spring-boot-starter-webflux'
```

## 공통 모듈
```yaml
implementation project(":util")
```
```java
import com.example.util.helloUtil.HelloUtil;

@RestController
@RequestMapping("/user")
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		// import from other module
		HelloUtil.hello();
		return "Hello From User App";
	}
}

```
## Usage
- http://localhost:8761/ // 여기서 연결 잘 됐는지 확인
- gateway 주소 :8080으로 요청시 자동으로 포트포워딩
- 처음에 게이트웨이 시작되는데 시간 좀 걸림
- :8080/user/hello -> :8083/user/hello
