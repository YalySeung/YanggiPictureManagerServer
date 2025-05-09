package com.yanggi.yanggipicturemanagerserver.service;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.PhotoTag;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoTagService {

    @Autowired
    PhotoTagRepository tagRepository;

    public void addTag(Photo photo, String tag) {
        PhotoTag tagEntity = PhotoTag.builder()
                .photo(photo)
                .tag(tag)
                .build();
        tagRepository.save(tagEntity);
    }

    public List<PhotoTag> getTags(Photo photo) {
        return tagRepository.findByPhoto(photo);
    }

    public void removeTag(Long photoId, String tag) {
        List<PhotoTag> tags = tagRepository.findByPhoto_PhotoIdAndTag(photoId, tag);
        tagRepository.deleteAll(tags);
    }
}