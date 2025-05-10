package com.yanggi.yanggipicturemanagerserver.controller;

import com.yanggi.yanggipicturemanagerserver.model.dto.PhotoResponse;
import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @AuthenticationPrincipal UserDetails user) {
        String filename = photoService.storeFile(file, user.getUsername());
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> delete(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/download/zip")
    public ResponseEntity<Resource> downloadZip(@RequestBody List<String> filenames) {
        Resource zip = photoService.createZip(filenames);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zip.getFilename())
                .body(zip);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable Long id) {
        Photo photo = photoService.findById(id);
        
        return ResponseEntity.ok(photo);
    }

    @GetMapping
    public List<PhotoResponse> getMyPhotos(@AuthenticationPrincipal UserDetails userDetails) {
        return photoService.getPhotosByUsername(userDetails.getUsername());
    }
}

