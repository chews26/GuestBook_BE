package com.guestbook_be.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Entry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false, length = 255)
    private String deleteCodeHash;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (likeCount == null) likeCount = 0;
    }
}