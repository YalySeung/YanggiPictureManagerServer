package com.yanggi.yanggipicturemanagerserver.controller;

import com.yanggi.yanggipicturemanagerserver.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        String filename = photoService.storeFile(file);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadPhotos(@RequestParam("ids") String ids) {
        // ids = "uuid1,uuid2,uuid3"
        Resource zipFile = photoService.createZip(ids.split(","));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"favorites.zip\"")
                .body(zipFile);
    }
}

