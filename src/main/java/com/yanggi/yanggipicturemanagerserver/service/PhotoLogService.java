package com.yanggi.yanggipicturemanagerserver.service;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.PhotoLog;
import com.yanggi.yanggipicturemanagerserver.model.entity.User;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoLogRepository;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoLogService {

    @Autowired
    PhotoLogRepository photoLogRepository;

    @Autowired
    PhotoRepository photoRepository;

    public void logAction(Photo photo, User user, String action, String detail) {
        PhotoLog log = PhotoLog.builder()
                .photo(photo)
                .user(user)
                .action(action)
                .detail(detail)
                .build();
        photoLogRepository.save(log);
    }

    public List<PhotoLog> getLogsByPhoto(Photo photo) {
        return photoLogRepository.findByPhoto(photo);
    }

    public List<PhotoLog> getLogsByPhotoId(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("사진 없음"));
        return photoLogRepository.findByPhoto(photo);
    }
}