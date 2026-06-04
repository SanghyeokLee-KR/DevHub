package com.icia.devhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class securityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 기존 AJAX/폼이 CSRF 토큰을 보내지 않으므로 우선 비활성화 (추후 토큰 적용 시 활성화)
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 로그인해야만 가능한 민감 경로: 회원 수정/탈퇴, 글·댓글·팀 작성/수정/삭제, 결제, 출석
                .requestMatchers(
                    "/modiForm/**", "/deleteForm/**", "/mModify", "/mDelete", "/mView/**", "/logHistory",
                    "/write", "/bWrite", "/modifyForm/**", "/bModify", "/bDelete/**", "/insertWrite",
                    "/commentWrite", "/commentModify", "/commentDelete",
                    "/charge", "/deductPoints", "/getproduct", "/pay",
                    "/attend", "/EventAtt",
                    "/TeamWrite", "/insertTeam", "/insertProject",
                    "/teamResumeSubmit", "/teamModifyForm/**", "/teamDelete/**"
                ).authenticated()
                // 그 외(메인·목록/상세 조회·로그인/회원가입·정적 리소스 등)는 모두 허용
                .anyRequest().permitAll()
            )
            // 세션의 loginId를 Spring Security 인증 주체로 연결하는 브릿지 필터
            .addFilterBefore(new SessionAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            // 미인증 사용자가 보호 경로에 접근하면 로그인 페이지로 이동
            .exceptionHandling(ex -> ex.authenticationEntryPoint(
                (request, response, authException) -> response.sendRedirect("/login")))
            // 앱이 자체 /mLogout 을 사용하므로 Spring Security 기본 logout 비활성화
            .logout(logout -> logout.disable());

        return http.build();
    }
}
