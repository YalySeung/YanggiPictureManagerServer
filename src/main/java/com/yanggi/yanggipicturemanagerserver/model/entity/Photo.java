package com.yanggi.yanggipicturemanagerserver.model.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(length = 255)
    private String originalName;

    @Column(length = 20)
    private String fileType;

    private Long fileSize;

    @Column(length = 100)
    private String tag;

    private LocalDateTime uploadedAt;

    private Boolean deleted;
}