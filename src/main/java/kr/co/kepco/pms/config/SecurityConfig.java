package kr.co.kepco.pms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS 설정 (람다식 필수)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. CSRF, Form Login, HTTP Basic 비활성화
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // 3. ★ 핵심: API 경로별 접근 권한 (requestMatchers 사용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/logout").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasRole("USER")
                        .anyRequest().authenticated()
                )

                // 4. 세션 관리 (동시 접속 제어 역시 람다식 내부로 편입됨)
                .sessionManagement(session -> session
                        .sessionConcurrency(concurrency -> concurrency
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                        )
                );

        return http.build();
    }

    // 비밀번호 암호화 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 설정 빈 (List.of 사용으로 깔끔하게 작성)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}