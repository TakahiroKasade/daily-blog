package com.dailycoding.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String nickname;

    private LocalDateTime createdTime;

    // 多對一關聯：多則留言屬於一篇文章
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 自訂建構子方便使用
    public Comment(String content, String nickname, Post post) {
        this.content = content;
        this.nickname = nickname;
        this.post = post;
        this.createdTime = LocalDateTime.now();
    }
}
