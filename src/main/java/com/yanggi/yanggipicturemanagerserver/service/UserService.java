package com.yanggi.yanggipicturemanagerserver.service;

import com.yanggi.yanggipicturemanagerserver.model.entity.User;
import com.yanggi.yanggipicturemanagerserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password("{noop}"+user.getPassword()) // 암호화된 비번일 것
                .roles(user.getRole())
                .build();
    }

    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
    }

}
