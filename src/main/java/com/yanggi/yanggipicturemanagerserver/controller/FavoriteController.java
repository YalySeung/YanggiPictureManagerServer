package com.yanggi.yanggipicturemanagerserver.controller;

import com.yanggi.yanggipicturemanagerserver.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/{photoId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long photoId,
                                            @AuthenticationPrincipal UserDetails user) {
        favoriteService.add(user.getUsername(), photoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long photoId,
                                               @AuthenticationPrincipal UserDetails user) {
        favoriteService.remove(user.getUsername(), photoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getFavorites(@AuthenticationPrincipal UserDetails user) {
        List<String> favorites = favoriteService.getFilenames(user.getUsername());
        return ResponseEntity.ok(favorites);
    }
}
