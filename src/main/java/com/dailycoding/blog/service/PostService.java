package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.Post;
import com.dailycoding.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    //4.儲存文章方法
    public void savePost(Post post) {
        if(post.getCreatedTime() ==  null){
            post.setCreatedTime(LocalDateTime.now());
        }
        postRepository.save(post);
    }

}
