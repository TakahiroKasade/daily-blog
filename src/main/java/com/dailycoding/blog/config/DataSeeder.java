package com.dailycoding.blog.config;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.entity.Project;
import com.dailycoding.blog.repository.PostRepository;
import com.dailycoding.blog.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private PostRepository postRepository;
    private ProjectRepository projectRepository;

    public DataSeeder(PostRepository postRepository, ProjectRepository projectRepository) {
        this.postRepository = postRepository;
        this.projectRepository = projectRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        if(postRepository.count()==0){
            Post p1 = new Post("第一篇部落格", "這是我的第一篇文章內容...", LocalDateTime.now());
            Post p2 = new Post("學習 Spring Boot", "Spring Boot 真是太方便了！", LocalDateTime.now());

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

    }

}
