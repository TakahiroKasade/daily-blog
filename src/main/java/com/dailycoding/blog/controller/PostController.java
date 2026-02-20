package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.service.MarkdownService;
import com.dailycoding.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;
    private final MarkdownService markdownService; // 注入 MarkdownService

    public PostController(PostService postService, MarkdownService markdownService) {
        this.postService = postService;
        this.markdownService = markdownService;
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

    // 新增：單篇文章閱讀頁面
    @GetMapping("posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/"; // 找不到文章回到首頁
        }
        
        // 將 Markdown 內容轉換為 HTML
        // 將 Markdown 內容轉換為 HTML
        String htmlContent = markdownService.renderToHtml(post.getContent());
        
        // 取得相關文章
        List<Post> relatedPosts = postService.getRelatedPosts(post);
        
        model.addAttribute("post", post);
        model.addAttribute("htmlContent", htmlContent); // 傳遞轉換後的 HTML
        model.addAttribute("relatedPosts", relatedPosts); // 傳遞相關文章

        return "post"; // 對應 templates/post.html
    }

}

