package com.yanggi.yanggipicturemanagerserver.repository;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.PhotoTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoTagRepository extends JpaRepository<PhotoTag, Long> {
    List<PhotoTag> findByPhoto(Photo photo);

    List<PhotoTag> findByPhoto_PhotoIdAndTag(Long photoId, String tag);
}