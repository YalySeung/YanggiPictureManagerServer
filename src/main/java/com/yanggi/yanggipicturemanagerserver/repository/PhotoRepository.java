package com.yanggi.yanggipicturemanagerserver.repository;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByUserAndDeletedFalse(User user);
}
