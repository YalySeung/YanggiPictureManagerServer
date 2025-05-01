package com.yanggi.yanggipicturemanagerserver.service;

import com.yanggi.yanggipicturemanagerserver.model.Photo;
import com.yanggi.yanggipicturemanagerserver.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class PhotoService {

    private final Path uploadDir = Paths.get("uploads");
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리 생성 실패", e);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            String ext = getFileExtension(originalName);
            String uuid = UUID.randomUUID().toString() + "." + ext;
            Path target = uploadDir.resolve(uuid);
            file.transferTo(target);

            // DB에 메타데이터 저장
            Photo photo = Photo.builder()
                    .originalName(originalName)
                    .storedName(uuid)
                    .fileType(ext)
                    .tags("기타") // 기본 태그 처리, 나중에 프론트에서 받아도 됨
                    .uploadedAt(LocalDateTime.now())
                    .build();
            photoRepository.save(photo);

            return uuid;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    public Resource createZip(String[] storedNames) {
        try {
            String zipFilename = "favorites_" + UUID.randomUUID() + ".zip";
            Path zipPath = uploadDir.resolve(zipFilename);
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
                for (String name : storedNames) {
                    Path file = uploadDir.resolve(name);
                    if (Files.exists(file)) {
                        zos.putNextEntry(new ZipEntry(file.getFileName().toString()));
                        Files.copy(file, zos);
                        zos.closeEntry();
                    }
                }
            }
            return new FileSystemResource(zipPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("ZIP 생성 실패", e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot >= 0) ? filename.substring(dot + 1).toLowerCase() : "";
    }

    public void deletePhoto(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사진을 찾을 수 없습니다: id=" + id));

        // 파일 삭제
        Path filePath = uploadDir.resolve(photo.getStoredName());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + photo.getStoredName(), e);
        }

        // DB 삭제
        photoRepository.deleteById(id);
    }

}
