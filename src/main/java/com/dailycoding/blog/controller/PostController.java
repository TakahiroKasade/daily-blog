package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("posts/new")
    public String showCreateForm(Model model){
        Post post = new Post();
        model.addAttribute("post", post);

        return "create_post";
    }

    @PostMapping("posts")
    public String createPost(@ModelAttribute Post post){

        postService.savePost(post);

        return  "redirect:/";
    }

    @GetMapping("posts/delete/{id}")
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("posts/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "edit_post";
    }

    @PostMapping("posts/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post){
        post.setId(id);
        postService.savePost(post);
        return "redirect:/";
    }


}
