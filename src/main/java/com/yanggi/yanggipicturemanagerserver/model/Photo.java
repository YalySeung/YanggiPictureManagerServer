package com.yanggi.yanggipicturemanagerserver.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName; // 원래 파일명
    private String storedName;   // 저장된 파일명 (UUID)
    private String fileType;     // jpg, png, pdf 등
    private String tags;         // 태그 (ex: 여행,가족)

    private LocalDateTime uploadedAt;
}
