package com.dailycoding.blog.repository;

import com.dailycoding.blog.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    // 根據名稱搜尋作品
    // 對應 SQL: SELECT * FROM projects WHERE name LIKE '%keyword%'
    List<Project> findByNameContaining(String keyword);
}
