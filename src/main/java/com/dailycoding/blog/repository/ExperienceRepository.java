package com.dailycoding.blog.repository;

import com.dailycoding.blog.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    List<Experience> findByCategory(String category);
}
