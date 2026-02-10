package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    // 注入 Repository
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    // 1. 取得所有文章
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 2. 儲存文章方法
    public void savePost(Post post) {
        if(post.getCreatedTime() ==  null){
            post.setCreatedTime(LocalDateTime.now());
        }
        postRepository.save(post);
    }

    // 3. 刪除方法
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 4. 更新/修改
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

}
