package com.dailycoding.blog.controller;

import com.dailycoding.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String nickname,
                             @RequestParam String content) {
        
        commentService.addComment(postId, nickname, content);
        
        // 導回文章頁面 (帶上 hash 錨點，直接跳到留言區)
        return "redirect:/posts/" + postId + "#comments";
    }
}
