package com.yanggi.yanggipicturemanagerserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login-success")
    public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok("로그인 성공: " + user.getUsername());
    }

    @GetMapping("/me")
    public ResponseEntity<String> currentUser(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(user.getUsername());
    }
}