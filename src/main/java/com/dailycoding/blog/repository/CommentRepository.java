package com.dailycoding.blog.repository;

import com.dailycoding.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 根據文章 ID 查詢留言，並按時間排序 (新的在後)
    List<Comment> findByPostIdOrderByCreatedTimeAsc(Long postId);
}
