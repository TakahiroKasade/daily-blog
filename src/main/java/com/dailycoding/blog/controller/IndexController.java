package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.service.PostService;
import com.dailycoding.blog.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final PostService postService;
    private final ProjectService projectService;

    public IndexController(PostService postService, ProjectService projectService) {
        this.postService = postService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String index(Model model){

        List<Post>  posts = postService.getAllPosts();
        List<Project>  projects = projectService.getAllProjects();

        model.addAttribute("posts",posts);
        model.addAttribute("projects",projects);

        return "index";
    }

}
