package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.repository.PostRepository;
import com.dailycoding.blog.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    // 2. 注入 Repository
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    // 3. 業務方法：取得所有文章
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

}
