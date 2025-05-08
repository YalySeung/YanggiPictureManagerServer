package com.yanggi.yanggipicturemanagerserver.repository;

import com.yanggi.yanggipicturemanagerserver.model.entity.Favorite;
import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndPhoto(User user, Photo photo);
    List<Favorite> findByUser(User user);
}