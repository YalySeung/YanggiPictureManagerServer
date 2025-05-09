package com.yanggi.yanggipicturemanagerserver.service;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import com.yanggi.yanggipicturemanagerserver.model.entity.User;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoRepository;
import com.yanggi.yanggipicturemanagerserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class PhotoService {

    private final Path uploadDir;

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;

    public PhotoService(PhotoRepository photoRepository, UserRepository userRepository,  @Value("${app.upload.dir}") String uploadPath) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.uploadDir = Paths.get(uploadPath);

        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리 생성 실패", e);
        }
    }

    public String storeFile(MultipartFile file, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("사용자 없음: " + username));

            String originalName = file.getOriginalFilename();
            String ext = getFileExtension(originalName);
            String uuidName = UUID.randomUUID().toString() + "." + ext;
            Path target = uploadDir.resolve(uuidName);
            file.transferTo(target);

            Photo photo = Photo.builder()
                    .user(user)
                    .filename(uuidName)
                    .originalName(originalName)
                    .fileType(ext)
                    .fileSize(file.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .deleted(false)
                    .build();

            photoRepository.save(photo);
            return uuidName;

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    public Resource createZip(List<String> filenames) {
        try {
            String zipName = "favorites_" + UUID.randomUUID() + ".zip";
            Path zipPath = uploadDir.resolve(zipName);
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
                for (String name : filenames) {
                    Path file = uploadDir.resolve(name);
                    if (Files.exists(file)) {
                        zos.putNextEntry(new ZipEntry(name));
                        Files.copy(file, zos);
                        zos.closeEntry();
                    }
                }
            }
            return new FileSystemResource(zipPath);
        } catch (IOException e) {
            throw new RuntimeException("ZIP 생성 실패", e);
        }
    }

    public void deletePhoto(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("사진을 찾을 수 없습니다. id=" + photoId));

        Path filePath = uploadDir.resolve(photo.getFilename());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + photo.getFilename(), e);
        }

        photoRepository.delete(photo);
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot >= 0) ? filename.substring(dot + 1).toLowerCase() : "";
    }

    public Photo findById(Long id) {
        return photoRepository.findById(id).orElseThrow(() -> new RuntimeException("사진 없음"));
    }

    public List<Photo> getPhotosByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        return photoRepository.findByUserAndDeletedFalse(user);
    }
}
