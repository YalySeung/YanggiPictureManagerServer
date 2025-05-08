package com.yanggi.yanggipicturemanagerserver.model.entity;


import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photo_logs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String action;

    private LocalDateTime timestamp;

    @Lob
    private String detail;
}
