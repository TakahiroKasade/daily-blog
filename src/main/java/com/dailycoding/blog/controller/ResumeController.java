package com.dailycoding.blog.controller;

import com.dailycoding.blog.entity.Experience;
import com.dailycoding.blog.service.ExperienceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 履歷頁面控制器
 * 負責經歷資料的 CRUD 操作與頁面導向
 */
@Controller
public class ResumeController {

    private ExperienceService experienceService;

    public ResumeController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    /**
     * 顯示履歷頁面（時間軸）
     * @param model 傳遞經歷清單給前端
     * @return resume.html
     */
    @GetMapping("/resume")
    public String showResume(Model model) {
       List<Experience> experiences = experienceService.getAllExperiences();
       model.addAttribute("experiences", experiences);
       return "resume";
    }

    /**
     * 顯示新增經歷表單
     * @param model 傳遞空的 Experience 物件給表單綁定
     * @return create_experience.html
     */
    @GetMapping("/experiences/new")
    public String showCreateExperienceForm(Model model) {
        Experience experience = new Experience();
        model.addAttribute("experience", experience);
        return "create_experience";
    }

    /**
     * 儲存新經歷
     * @param experience 表單提交的經歷資料（由 @ModelAttribute 自動綁定）
     * @return 重導向至履歷頁面
     */
    @PostMapping("/experiences")
    public String createExperience(@ModelAttribute Experience experience) {
        experienceService.saveExperience(experience);
        return "redirect:/resume";
    }

    /**
     * 顯示編輯經歷表單
     * @param id 要編輯的經歷 ID
     * @param model 傳遞該筆經歷資料給表單
     * @return edit_experience.html
     */
    @GetMapping("/experiences/edit/{id}")
    public String editExperienceForm(@PathVariable Long id, Model model) {
        Experience experience = experienceService.getExperienceById(id);
        model.addAttribute("experience", experience);
        return "edit_experience";
    }

    /**
     * 更新經歷
     * @param id 要更新的經歷 ID
     * @param experience 表單提交的更新資料
     * @return 重導向至履歷頁面
     */
    @PostMapping("/experiences/edit/{id}")
    public String updateExperience(@PathVariable Long id, @ModelAttribute Experience experience) {
        experience.setId(id);
        experienceService.saveExperience(experience);
        return "redirect:/resume";
    }

    /**
     * 刪除經歷
     * @param id 要刪除的經歷 ID
     * @return 重導向至履歷頁面
     */
    @GetMapping("/experiences/delete/{id}")
    public String deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return "redirect:/resume";
    }

    
}

