package com.yanggi.yanggipicturemanagerserver.service;

import com.yanggi.yanggipicturemanagerserver.model.entity.Favorite;
import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.User;
import com.yanggi.yanggipicturemanagerserver.repository.FavoriteRepository;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoRepository;
import com.yanggi.yanggipicturemanagerserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PhotoRepository photoRepository;

    public Optional<Favorite> find(User user, Photo photo) {
        return favoriteRepository.findByUserAndPhoto(user, photo);
    }
    public Favorite add(String username, Long photoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("사진 없음"));

        return favoriteRepository.save(Favorite.builder()
                .user(user)
                .photo(photo)
                .build());
    }

    public void remove(String username, Long photoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("사진 없음"));

        Favorite fav = favoriteRepository.findByUserAndPhoto(user, photo)
                .orElseThrow(() -> new RuntimeException("즐겨찾기 없음"));
        favoriteRepository.delete(fav);
    }

    public List<Favorite> getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        return favoriteRepository.findByUser(user);
    }

    public List<String> getFilenames(String username) {
        return getByUsername(username).stream()
                .map(fav -> fav.getPhoto().getFilename())
                .toList();
    }
}