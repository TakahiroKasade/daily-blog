package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.repository.PostRepository;
import com.dailycoding.blog.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository  projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects(){
        return  projectRepository.findAll();
    }

}
