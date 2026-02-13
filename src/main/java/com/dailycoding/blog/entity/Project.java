package com.dailycoding.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String  imageUrl;
    private String websiteUrl;

    // 自訂建構子如果不被 Lombok @AllArgsConstructor 覆蓋，或是需要特定參數的建構子，可以保留
    public Project(String name, String description, String imageUrl, String websiteUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
    }
}
