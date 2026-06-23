package com.dailycoding.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operator;    // 操作者
    private String operation;   // 動作描述
    private String method;      // 呼叫方法
    
    @Column(columnDefinition = "TEXT")
    private String params;      // 請求參數
    
    private String status;      // 狀態 (SUCCESS/FAIL)
    private Long executionTime; // 執行時間 (ms)
    private LocalDateTime createdAt; // 紀錄時間
}
