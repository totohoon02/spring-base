package com.example.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
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

        // 커스텀 폼 로그인
        http.formLogin(auth -> auth
                .loginPage("/login") // 자동 리다이렉트
                .loginProcessingUrl("/loginProc") // 폼 요청을 어디로 보낼 것인지
                .permitAll());

        // http basic login
        // MSA에서 서버 간 통신할 때 사용
        // http.httpBasic(Customizer.withDefaults());

        // 자동 설정, 개발시에만 비활성화
        // CSRF: 사이트간 요청 위조, 공격자가 자신의 웹사이트를 통해 사용자의 웹사이트에 요청을 보내는 것
        // http.csrf(auth -> auth.disable());
        // CSRF 토큰 검증 필요 -> FE에서 토큰을 가져와야 함
        // GET 요청은 토큰 검증을 하지 않음 -> 로그아웃,

        // 로그아웃
        http.logout(auth -> auth
                .logoutUrl("/logout") // 로그아웃 URL
                .logoutSuccessUrl("/")); // 로그아웃 후 리다이렉트

        // 세션 설정
        http.sessionManagement(auth -> auth
                .maximumSessions(1) // 다중 로그인 수
                .maxSessionsPreventsLogin(true)); // 다중 로그인 시 차단

        http.sessionManagement(auth -> auth
                .sessionFixation().changeSessionId()); // 동일한 세션, id 변경

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // 단방향 암호화 -> 디코딩 불가
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("USER")
                .build();
    }
}
