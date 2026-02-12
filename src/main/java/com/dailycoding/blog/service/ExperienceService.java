package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Experience;
import com.dailycoding.blog.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    //回傳所有經歷
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    //回傳指定分類的經歷
    public List<Experience> getByCategory(String category) {
        return experienceRepository.findByCategory(category);
    }

    //儲存經歷
    public void saveExperience(Experience experience) {
        experienceRepository.save(experience);
    }
    //根據id找經歷
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }
    //刪除經歷
    public void deleteExperienceById(Long id) {
        experienceRepository.deleteById(id);
    }
}
