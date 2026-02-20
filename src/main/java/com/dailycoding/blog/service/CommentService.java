package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Comment;
import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.repository.CommentRepository;
import com.dailycoding.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void addComment(Long postId, String nickname, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + postId));
        
        Comment comment = new Comment(content, nickname, post);
        commentRepository.save(comment);
    }
    
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedTimeAsc(postId);
    }
}
