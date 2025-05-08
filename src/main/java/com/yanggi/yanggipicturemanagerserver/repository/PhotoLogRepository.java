package com.yanggi.yanggipicturemanagerserver.repository;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.PhotoLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoLogRepository extends JpaRepository<PhotoLog, Long> {
    List<PhotoLog> findByPhoto(Photo photo);
}