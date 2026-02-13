package com.dailycoding.blog.config;

import com.dailycoding.blog.entity.Experience;
import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.entity.User;
import com.dailycoding.blog.repository.ExperienceRepository;
import com.dailycoding.blog.repository.PostRepository;
import com.dailycoding.blog.repository.ProjectRepository;
import com.dailycoding.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private PostRepository postRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ExperienceRepository experienceRepository;

    public DataSeeder(PostRepository postRepository, ProjectRepository projectRepository, UserRepository userRepository,
                      PasswordEncoder  passwordEncoder, ExperienceRepository experienceRepository) {
        this.postRepository = postRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.experienceRepository = experienceRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        if(postRepository.count()==0){
            Post p1 = new Post("第一篇部落格", "這是我的第一篇文章內容...", LocalDateTime.now(), "Life");
            Post p2 = new Post("學習 Spring Boot", "Spring Boot 真是太方便了！", LocalDateTime.now(), "Tech");

            postRepository.saveAll(Arrays.asList(p1,p2));
            System.out.println("✅ 預設文章資料已建立！");
        }

        if (projectRepository.count() == 0) {
            Project pj1 = new Project(
                    "Retrograde Asteroid",
                    "一個復古射擊遊戲，使用 Java Swing 開發。",
                    "https://placehold.co/600x400",
                    "https://github.com/yourname/retrograde-asteroid"
            );
            projectRepository.save(pj1);
            System.out.println("✅ 預設作品資料已建立！");
        }

        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRole("ADMIN");
            userRepository.save(user);
            System.out.println("管理者帳號已建立");
        }

        if (experienceRepository.count() == 0) {
            Experience e1 = new Experience("Java 工程師", "某科技公司", "負責後端 API 開發",
                    "2024-06", "至今", "WORK", "Java, Spring Boot, SQL");
            Experience e2 = new Experience("資訊管理系", "某大學", "主修程式設計與資料庫",
                    "2020-09", "2024-06", "EDUCATION", "Java, Python, Database");
            Experience e3 = new Experience("Daily Blog", "Side Project", "個人技術部落格系統",
                    "2026-01", "至今", "PROJECT", "Spring Boot, Thymeleaf, PostgreSQL");
            experienceRepository.saveAll(Arrays.asList(e1, e2, e3));
            System.out.println("✅ 預設履歷資料已建立！");
                        
        }


    }

}
