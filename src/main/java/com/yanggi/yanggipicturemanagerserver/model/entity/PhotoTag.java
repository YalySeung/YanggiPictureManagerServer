package com.yanggi.yanggipicturemanagerserver.model.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "photo_tags")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    @Column(length = 100)
    private String tag;
}
