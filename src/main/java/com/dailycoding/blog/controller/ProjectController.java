package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProjectController {

    private ProjectService projectService;
    private com.dailycoding.blog.service.FileStorageService fileStorageService;

    public ProjectController(ProjectService projectService, com.dailycoding.blog.service.FileStorageService fileStorageService){
        this.projectService = projectService;
        this.fileStorageService = fileStorageService;
    }

    //專案列表頁面
    @GetMapping("projects")
    public String showProjects(Model model){
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }

    //新增專案表單
    @GetMapping("/projects/new")
    public String newProject(Model model){
        Project project = new Project();
        model.addAttribute("project", project);
        return "create_project";
    }

    //儲存專案
    @PostMapping("/projects")
    public String saveProject(@ModelAttribute Project project, 
                              @org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file){
        
        // 儲存圖片
        if (!file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            project.setImageUrl(fileName); // 重用 imageUrl 欄位存檔名
        }
        
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    //編輯專案表單
    @GetMapping("projects/edit/{id}")
    public String showEditProjectForm(Model model, @PathVariable Long id){
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "edit_project";
    }

    //更新專案
    @PostMapping("projects/edit/{id}")
    public String  updateProject(@PathVariable Long id, @ModelAttribute Project project,
                                 @org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file){
        
        // 取得舊的專案資訊
        Project existingProject = projectService.getProjectById(id);

        if (!file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            project.setImageUrl(fileName);
        } else {
            project.setImageUrl(existingProject.getImageUrl());
        }

        project.setId(id);
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    //刪除專案
    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return "redirect:/projects";
    }


}
