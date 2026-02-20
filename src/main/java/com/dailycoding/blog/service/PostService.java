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

    //  5. 搜尋文章
    public List<Post> searchPosts(String keyword){
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }

    // 6. 根據分類取得文章 (強制重新編譯)
    public List<Post> getPostsByCategory(String category) {
        return postRepository.findByCategory(category);
    }
    
    // 7. 取得相關文章 (同分類，排除自己，取前3篇)
    public List<Post> getRelatedPosts(Post currentPost) {
        return postRepository.findTop3ByCategoryAndIdNotOrderByCreatedTimeDesc(currentPost.getCategory(), currentPost.getId());
    }

}
