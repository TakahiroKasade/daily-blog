package com.dailycoding.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdTime;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'Tech'") // 設定資料庫預設值
    private String category = "Tech"; 

    // 自訂建構子如果不被 Lombok @AllArgsConstructor 覆蓋，或是需要特定參數的建構子，可以保留
    public Post(String title, String content, LocalDateTime createdTime, String category) {
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.category = category;
    }

    // 新增：計算閱讀時間 (假設每分鐘閱讀 300 字)
    // 注意：Lombok @Data 不會自動生成這個商業邏輯方法，所以要保留
    public String getReadingTime() {
        if (content == null || content.isEmpty()) {
            return "1 min read";
        }
        int words = content.length();
        int minutes = (int) Math.ceil(words / 300.0);
        return minutes + " min read";
    }
}
