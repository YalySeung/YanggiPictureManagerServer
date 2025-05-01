package com.yanggi.yanggipicturemanagerserver.config;

import com.yanggi.yanggipicturemanagerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/photos/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .userDetailsService(userService);

        return http.build();
    }
}

