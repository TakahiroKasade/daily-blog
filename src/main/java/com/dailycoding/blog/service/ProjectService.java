package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository  projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    //取得所有項目
    public List<Project> getAllProjects(){
        return  projectRepository.findAll();
    }

    //儲存專案
    public void saveProject(Project project){
        projectRepository.save(project);
    }

    //根據ID取得專案
    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElse(null);
    }

    //刪除專案
    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }


}
