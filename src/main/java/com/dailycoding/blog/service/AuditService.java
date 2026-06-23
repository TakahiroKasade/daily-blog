package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.AuditLog;
import com.dailycoding.blog.repository.AuditLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * 獲取使用者的行為統計數據，具備身份安全校驗
     * @param targetUser 管理員想查看的對象 (若非管理員則強制查自己)
     */
    public Map<String, Object> getUserActivityStats(String targetUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // 安全邏輯：如果不是管理員，無視傳入的 targetUser，強制使用當前使用者
        String finalTarget = (isAdmin && targetUser != null && !targetUser.isEmpty()) ? targetUser : currentUser;

        Map<String, Object> stats = new HashMap<>();
        stats.put("username", finalTarget);
        stats.put("activity", auditLogRepository.findLast7DaysActivityByOperator(finalTarget));
        stats.put("distribution", auditLogRepository.findOperationDistributionByOperator(finalTarget));
        stats.put("recentLogs", auditLogRepository.findTop10ByOperatorOrderByCreatedAtDesc(finalTarget));

        return stats;
    }
}
