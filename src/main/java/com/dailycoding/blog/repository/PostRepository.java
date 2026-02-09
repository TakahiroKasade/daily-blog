package com.dailycoding.blog.repository;

import com.dailycoding.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    // 例如：想找標題包含某個關鍵字的文章
    // 對應 SQL: SELECT * FROM posts WHERE title LIKE '%keyword%'
    List<Post> findByTitleContains(String keyword);
}
