package com.dailycoding.blog.repository;

import com.dailycoding.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    // 搜尋標題 或 內容 包含關鍵字 (忽略大小寫)
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    // 根據分類搜尋文章
    List<Post> findByCategory(String category);
}
