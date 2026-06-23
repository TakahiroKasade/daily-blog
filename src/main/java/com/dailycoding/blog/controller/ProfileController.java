package com.dailycoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/activity")
    public String showActivityPage() {
        return "profile/activity";
    }
}
