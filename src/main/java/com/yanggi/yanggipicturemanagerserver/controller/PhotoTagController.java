package com.yanggi.yanggipicturemanagerserver.controller;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.PhotoTag;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoRepository;
import com.yanggi.yanggipicturemanagerserver.service.PhotoTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class PhotoTagController {

    private final PhotoTagService photoTagService;
    private final PhotoRepository photoRepository;

    @PostMapping("/{photoId}")
    public void addTag(@PathVariable Long photoId, @RequestParam String tag) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("사진 없음"));
        photoTagService.addTag(photo, tag);
    }

    @GetMapping("/{photoId}")
    public List<PhotoTag> getTags(@PathVariable Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("사진 없음"));
        return photoTagService.getTags(photo);
    }

    @DeleteMapping("/{photoId}")
    public void deleteTag(@PathVariable Long photoId, @RequestParam String tag) {
        photoTagService.removeTag(photoId, tag);
    }
}