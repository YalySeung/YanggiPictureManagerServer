package com.yanggi.yanggipicturemanagerserver.config;

import com.yanggi.yanggipicturemanagerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/photos/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                .failureHandler((req, res, e) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .userDetailsService(userService);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ⚠️ 여기를 setAllowedOriginPatterns 로 해야 함
        config.setAllowedOriginPatterns(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        // ⚠️ 반드시 true
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

