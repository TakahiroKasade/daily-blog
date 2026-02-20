package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.service.MarkdownService;
import com.dailycoding.blog.service.PostService;
import com.dailycoding.blog.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private final PostService postService;
    private final ProjectService projectService;
    private final MarkdownService markdownService;

    public IndexController(PostService postService, ProjectService projectService, MarkdownService markdownService) {
        this.postService = postService;
        this.projectService = projectService;
        this.markdownService = markdownService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false)String keyword,
                        @RequestParam(required = false)String category) {

        List<Post> posts;

        if (keyword != null && !keyword.isEmpty()) {
            posts = postService.searchPosts(keyword);
            model.addAttribute("keyword", keyword);
        } else if (category!=null && !category.isEmpty()) {
            posts = postService.getPostsByCategory(category);
            model.addAttribute("category", category);
        } else {
            posts = postService.getAllPosts();
        }

        // 生成文章摘要 Map (ID -> Summary)
        Map<Long, String> summaries = new HashMap<>();
        for (Post post : posts) {
            summaries.put(post.getId(), markdownService.getExcerpt(post.getContent(), 100)); // 摘要 100 字
        }
        model.addAttribute("summaries", summaries);

        model.addAttribute("posts", posts);

        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);

        return "index";
    }

}
