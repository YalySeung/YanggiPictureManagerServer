package com.yanggi.yanggipicturemanagerserver.controller;

import com.yanggi.yanggipicturemanagerserver.model.entity.PhotoLog;
import com.yanggi.yanggipicturemanagerserver.service.PhotoLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class PhotoLogController {

    @Autowired
    PhotoLogService photoLogService;

    @GetMapping("/{photoId}")
    public List<PhotoLog> getLogsByPhoto(@PathVariable Long photoId) {
        return photoLogService.getLogsByPhotoId(photoId);
    }
}