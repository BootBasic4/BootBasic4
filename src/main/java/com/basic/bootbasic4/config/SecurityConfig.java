package com.basic.bootbasic4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {

    // 정적 리소스는 Security 필터 대상에서 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // 요청별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 관리자만 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 일반 사용자만 접근 가능
                        .requestMatchers(
                                "/questions/add",
                                "/questions/edit/**",
                                "/questions/delete/**",
                                "/answers/**"
                        ).hasRole("USER")

                        // 비회원 접근 가능
                        .requestMatchers(
                                "/",
                                "/index",
                                "/check-username",
                                "/check-nickname",
                                "/check-email",
                                "/signup",
                                "/login",
                                "/questions/**"
                        ).permitAll()

                        // 그 외 모든 요청은 로그인 필요
                        .anyRequest().authenticated()
                )

                // 로그인 설정
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")

                        // 로그인 성공 후 권한별 이동
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(auth ->
                                            auth.getAuthority().equals("ROLE_ADMIN"));

                            if (isAdmin) {
                                response.sendRedirect("/admin/reports");
                            } else {
                                response.sendRedirect("/");
                            }
                        })

                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}