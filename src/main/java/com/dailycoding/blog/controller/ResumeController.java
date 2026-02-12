package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Experience;
import com.dailycoding.blog.repository.ExperienceRepository;
import com.dailycoding.blog.service.ExperienceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ResumeController {

    private ExperienceService experienceService;

    public ResumeController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/resume")
    public String showResume(Model model) {
       List<Experience> experiences = experienceService.getAllExperiences();
       model.addAttribute("experiences", experiences);
       return "resume";
    }

}
