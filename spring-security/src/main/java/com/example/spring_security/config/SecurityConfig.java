package com.example.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인가 필터
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/join", "joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()); // 로그인한 사용자는 접근

        // 커스텀 로그인
        http.formLogin(auth -> auth
                .loginPage("/login") // 자동 리다이렉트
                .loginProcessingUrl("/loginProc") // 폼 요청을 어디로 보낼 것인지
                .permitAll());

        // 자동 설정, 개발시에만 비활성화
        http.csrf(auth -> auth.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // 단방향 암호화 -> 디코딩 불가
    }
}
