package com.yanggi.yanggipicturemanagerserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서 실제 사용자 정보 조회 (DB, 메모리 등)
        if (!username.equals("user1")) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        return User.builder()
                .username("user1")
                .password("{noop}pass1") // 비밀번호는 인코딩 되어 있어야 함
                .roles("USER")
                .build();
    }
}
