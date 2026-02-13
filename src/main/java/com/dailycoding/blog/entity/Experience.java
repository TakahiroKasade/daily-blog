package com.dailycoding.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String startDate;
    private String endDate;
    private String category;
    private String skills;

    // 自訂建構子如果不被 Lombok @AllArgsConstructor 覆蓋，或是需要特定參數的建構子，可以保留
    public Experience(String title, String company, String description, String startDate, String endDate, String category, String skills) {
        this.title = title;
        this.company = company;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.skills = skills;
    }
}
